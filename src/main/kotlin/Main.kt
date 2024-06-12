package bullscows

class CowsBulls {
    private var secretCode: MutableList<Int>
    private var code: List<Int>
    private var cows: Int
    private var bulls: Int

    init {
        secretCode = mutableListOf(9,3,0,5)
        code = listOf(9,3,0,5)
        cows = 0
        bulls = 4
    }

    fun checkCode() {
        var i = 1
        while (true) {
            println("Turn ${i++}:")
            code = readln().split("").subList(1, secretCode.size + 1).map { it.toInt() }
            cows =0; bulls = 0
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

    fun generateSecretCode() {
        println("Please, enter the secret code's length:")
        val codeLength = readln().toInt()
        if (codeLength !in 1..10) { println("Error: can't generate a secret number with a length of $codeLength because there aren't enough unique digits."); return }
        var pseudoRandomNumber: String
        while (true) {
            secretCode = mutableListOf()
            pseudoRandomNumber = System.nanoTime().toString().reversed().substring(2, 2 + codeLength + 1)
            if (pseudoRandomNumber[0] == '0') continue
            for (c in pseudoRandomNumber) {
                if (pseudoRandomNumber.count { it == c } == 1) secretCode.add(c.digitToInt())
                if (secretCode.size == codeLength) break
            }
            if (secretCode.size == codeLength) break else continue
        }
    }
}

fun main() {
    val cowsBulls = CowsBulls()
    cowsBulls.generateSecretCode()
    println("Okay, let's start a game!")
    cowsBulls.checkCode()
}