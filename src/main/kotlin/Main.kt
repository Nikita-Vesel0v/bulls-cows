package bullscows

import kotlin.random.Random


class CowsBulls {
    private lateinit var secretCode: MutableList<String>
    private lateinit var code: List<String>

    private var cntSymbols = 0

    private var cows: Int = 0
    private var bulls: Int = 0

    private val alphabet = "abcdefghijklmnopqrstuvwxyz"

    fun checkCode() {
        var i = 1
        while (true) {
            println("Turn ${i++}:")
            code = readln().split("").subList(1, secretCode.size + 1)
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
                break
            }
        }
    }
    private fun printResult() {
        println(
            "Grade: " + when {
                cows > 0 && bulls > 0 -> "$bulls bull(s) and $cows cow(s)"
                cows > 0 && bulls == 0 -> "$cows cow(s)"
                cows == 0 && bulls > 0 -> "$bulls bull(s)"
                else -> "None"
            }
        )
    }
    private fun addSymbolToSecretCode(symbol: String) {
        if (symbol !in secretCode) {
            secretCode.add(symbol)
        }
    }
    fun generateSecretCode() {
        println("Please, enter the secret code's length:")
        val codeLength = readln().toInt()
        println("Input the number of possible symbols in the code:")
        cntSymbols = readln().toInt()
        var cntLetters = 0
        var symbol: String
        if (cntSymbols > 10) { // 10 is count of digits
            cntLetters = Random.nextInt(0, cntSymbols - 10 + 1)
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
    fun gameCondition() {
        val bounds = when {
            cntSymbols > 11 -> "(0-9, a-${alphabet[cntSymbols - 10 - 1]})" // 10 is count of digits
            cntSymbols == 11 -> "(0-9, a)"
            else -> "(0-9)"
        }
        println("The secret is prepared: ${"*".repeat(secretCode.size)} $bounds.")
    }
}

fun main() {
    val cowsBulls = CowsBulls()
    cowsBulls.generateSecretCode()
    cowsBulls.gameCondition()
    println("Okay, let's start a game!")
    cowsBulls.checkCode()
}