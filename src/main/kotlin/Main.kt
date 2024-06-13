package bullscows

import kotlin.random.Random

class CowsBulls {
    private var secretCode = mutableListOf<String>()
    private var code = listOf<String>()
    var cntSymbols = 0
    private var cows = 0
    private var bulls = 0
    private val alphabet = "abcdefghijklmnopqrstuvwxyz"

    private fun printResult() = println("Grade: " + when {
        cows > 0 && bulls > 0 -> "$bulls bull${addLetterS(bulls)} and $cows cow${addLetterS(cows)}"
        cows > 0 && bulls == 0 -> "$cows cow${addLetterS(cows)}"
        cows == 0 && bulls > 0 -> "$bulls bull${addLetterS(bulls)}"
        else -> "None"
    })
    fun gameCondition() = println("The secret is prepared: ${"*".repeat(secretCode.size)} " + when {
        cntSymbols > 11 -> "(0-9, a-${alphabet[cntSymbols - 10 - 1]})" // 10 is count of digits
        cntSymbols == 11 -> "(0-9, a)"
        else -> "(0-9)"
    } + ".")

    fun checkCode() {
        var i = 1
        var input: String
        while (true) {
            println("Turn ${i++}:")
            input = readln()
            if (input.length == secretCode.size) {
                code = input.split("").subList(1, secretCode.size + 1)
            } else {
                println("Error: wrong length of input number")
                return
            }
            cows = 0; bulls = 0
            code.forEachIndexed { index, c ->
                when (c) {
                    secretCode[index] -> bulls++
                    in secretCode -> cows++
                }
            }
            printResult()
            if (bulls == secretCode.size) {
                println("Congratulations! You guessed the secret code.")
                return
            }
        }
    }
    private fun addSymbolToSecretCode(symbol: String) {
        if (symbol !in secretCode) {
            secretCode.add(symbol)
        }
    }
    fun generateSecretCode(codeLength: Int) {
        var cntLetters = 0
        var symbol: String
        if (cntSymbols > 10) { // 10 is count of digits
            cntLetters = Random.nextInt(0, codeLength + 1)
        }

        secretCode = mutableListOf()

        while ((secretCode.size < cntLetters)) { // add letter
            symbol = alphabet[Random.nextInt(0, cntLetters)].toString()
            addSymbolToSecretCode(symbol)
        }

        while (secretCode.size < codeLength) { // add numbers
            symbol = Random.nextInt(0, 10).toString()
            addSymbolToSecretCode(symbol)
        }
        secretCode.shuffle()
    }
}

fun addLetterS(value: Int) = if (value > 1) "s" else ""
fun main() {
    val cowsBulls = CowsBulls()
    cowsBulls.run {
        println("Please, enter the secret code's length:")
        var input = readln()
        val codeLength = input.toIntOrNull()
        if (codeLength == null || codeLength < 1) {
            println("Error: \"$input\" isn't a valid number.")
            return
        }

        println("Input the number of possible symbols in the code:")
        input = readln()
        val cntSymbols = input.toIntOrNull()
        when {
            cntSymbols == null -> {
                println("Error: \"$input\" isn't a valid number.")
                return
            }
            cntSymbols < codeLength -> {
                println("Error: it's not possible to generate a code with a length of $codeLength with $cntSymbols unique symbols.")
                return
            }
            cntSymbols > 36 -> {
                println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
                return
            }
            else -> {
                this.cntSymbols = cntSymbols
                this.generateSecretCode(codeLength) }
        }
    } // check secret code's length and count of symbols. Call generation secret code if everything is ok
    cowsBulls.gameCondition()
    println("Okay, let's start a game!")
    cowsBulls.checkCode()
}