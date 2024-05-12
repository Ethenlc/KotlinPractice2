import kotlin.random.Random

fun main() {
    // Predefined list of words
    val words = listOf("hangman", "computer", "programming", "developer", "openai", "kotlin")
    // Select a random word from the list
    val hiddenWord = words[Random.nextInt(words.size)]
    // Set to store guessed letters
    val guessedLetters = mutableSetOf<Char>()
    // List to store incorrect guesses
    val incorrectGuesses = mutableListOf<Char>()
    // Initial number of attempts
    var attempts = 10
    // Initial score
    var score = 0
    // Flag to track if hint has been used
    var hintUsed = false

    // Display welcome message and instructions
    println("Welcome to Hangman!")
    println("The hidden word has ${hiddenWord.length} letters.")
    println("You have $attempts attempts to guess the word.")
    println("Type 'status' to see your current score and used hints.")
    println("Type 'rules' to see the game rules.")

    // Main game loop
    while (attempts > 0) {
        println("\nAttempts left: $attempts")
        println("Word: ${getVisibleWord(hiddenWord, guessedLetters)}")
        println("Incorrect guesses: ${incorrectGuesses.joinToString(", ")}")
        println("Score: $score")
        println("Guess a letter, type 'hint' for a hint, 'status' to check status, 'rules' for rules, or 'quit' to exit:")

        // Read user input
        val input = readLine()?.toLowerCase()

        // Handle user input
        when {
            input == "quit" -> {
                println("Thanks for playing!")
                return
            }
            input == "status" -> {
                println("Current Score: $score")
                println("Hints Used: ${if (hintUsed) "Yes" else "No"}")
            }
            input == "rules" -> {
                // Display game rules
                println("Hangman is a word-guessing game.")
                println("You have a limited number of attempts to guess the hidden word.")
                println("You can type a letter to guess it.")
                println("If the letter is in the word, it will be revealed.")
                println("If not, you lose an attempt.")
                println("You can also ask for a hint, which will reveal one letter in the word.")
                println("Using a hint will decrease your score.")
                println("Your score is based on the number of attempts left and penalties.")
                println("Type 'quit' to exit the game at any time.")
            }
            input == "hint" -> {
                if (!hintUsed) {
                    // Provide a hint if not already used
                    val hintLetter = hiddenWord.filterNot { it in guessedLetters }.firstOrNull()
                    if (hintLetter != null) {
                        println("Hint: '$hintLetter' is in the word.")
                        hintUsed = true
                        score -= 5 // Penalty for using hint
                    } else {
                        println("No hints available.")
                    }
                } else {
                    println("You already used the hint.")
                }
            }
            input?.length == 1 && input[0].isLetter() -> {
                // Process a guessed letter
                val guess = input[0]
                if (guess in guessedLetters || guess in incorrectGuesses) {
                    println("You already guessed '$guess'. Try a different letter.")
                } else {
                    guessedLetters.add(guess)
                    if (guess !in hiddenWord) {
                        attempts--
                        incorrectGuesses.add(guess)
                        println("Sorry, '$guess' is not in the word.")
                    } else {
                        println("Good guess!")
                        score += 10
                    }
                }
                if (hiddenWord.all { it in guessedLetters }) {
                    score += attempts * 5 // Bonus points for remaining attempts
                    println("\nCongratulations! You guessed the word: '$hiddenWord'")
                    println("Final score: $score")
                    return
                }
            }
            else -> println("Invalid input. Please enter a valid letter, 'hint', 'status', 'rules', or 'quit'.")
        }
    }

    // Display result if no attempts left
    println("\nSorry, you've run out of attempts. The word was: '$hiddenWord'")
    println("Final score: $score")
}

// Function to get the visible word with guessed letters revealed
fun getVisibleWord(word: String, guessedLetters: Set<Char>): String {
    return word.map { if (it in guessedLetters) it else '_' }.joinToString(" ")
}
