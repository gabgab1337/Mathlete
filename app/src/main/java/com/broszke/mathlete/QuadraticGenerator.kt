package com.broszke.mathlete
import kotlin.math.sqrt
import kotlin.random.Random
import com.broszke.mathlete.calculateFraction
import kotlin.math.roundToInt

data class QuadraticFunction(val a: Int = 0, val b: Int = 0, var c: Int = 0, val delta: Int = 0)
class QuadraticGenerator : QuestionGenerator{
    var function: QuadraticFunction
    init{
        function = generateFunction()
    }
    fun generateFunction(): QuadraticFunction {
        val random = Random
        var a: Int
        var b: Int
        var c: Int
        var delta: Int
        val signDelta = random.nextInt(8)
        if (signDelta >= 3){ // 5/8 szansy na dodatnią deltę
            do {
                a = random.nextInt(10) + 1
                b = random.nextInt(20) - 10
                val n = random.nextInt(10) + 1
                delta = n * n
                c = b / 2 * b / 2 / a - delta
                delta = (b * b - 4 * a * c) // Liczenie delty na nowo aby nie wyszła delta nieperfekcyjna
            } while (a * c == 0 || !isPerfectSquare(delta) || delta >= 900)
        } else if (signDelta >= 1) { // 2/8 szansy na deltę równą 0
            do {
                a = random.nextInt(10) + 1
                b = random.nextInt(20) - 10
                delta = 0
                c = b / 2 * b / 2 / a
                delta = (b * b - 4 * a * c)
            } while (a * c == 0 || delta != 0)
        } else { // 1/8 szansy na ujemną deltę
            do {
                a = random.nextInt(10) + 1
                b = random.nextInt(20) - 10
                c = b * b / (4 * a) + 1
                delta = b * b - 4 * a * c // Liczenie delty na nowo aby nie wyszła delta nieperfekcyjna
            } while (a * c == 0 || delta >= 0)
        }
        print("a: $a, b: $b, c: $c, delta: $delta\n")
        return QuadraticFunction(a, b, c, delta)
    }

    private fun isPerfectSquare(n: Int): Boolean { // Helper do generatora
        val sqrt = Math.sqrt(n.toDouble())
        return sqrt.toInt() * sqrt.toInt() == n
    }

    fun calculateX(equals: Int = 0) : String {
        val a = function.a
        val b = function.b
        val delta = function.delta
        return if (delta < 0f) {
            "\\text{Brak miejsc zerowych}"
        } else if (delta == 0) {
            "x_{0} = ${calculateFraction((-b), (2 * a))}"
        } else {
            println(delta)
            val rootDelta = sqrt(delta.toDouble()).roundToInt()
            println(rootDelta)
            println((-b - rootDelta) / (2 * a))
            println((-b + rootDelta) / (2 * a))
            val nominator = -b - rootDelta
            val nominator2 = -b + rootDelta
            val denominator = 2 * a
            "x_{1} = ${calculateFraction(nominator, denominator)}\\;\\;\\;x_{2} = ${calculateFraction(nominator2, denominator)}"
        }
    }
    private fun calculateWrongX() : String{
        val random = Random
        val result1 = random.nextInt(20) - 10
        val result2 = random.nextInt(20) - 10 + result1
        return "x_{1} = $result1\\;\\;\\;x_{2} = $result2"
    }

    override fun generateQuestion(): QuizQuestion {
        function = generateFunction()
        val question = "Oblicz x dla:\n"
        val expression = "${if (function.a == 1) "" else if (function.a == -1) "-" else function.a}{x}^2" +
                "${if (function.b > 0) "+" else ""}${if (kotlin.math.abs(function.b) == 1) "" else function.b}x" +
                "${if (function.c > 0) "+" else ""}${function.c}=0"
        val correctAnswer = calculateX()
        var wrongAnswer1 = calculateWrongX()
        val wrongAnswer2 = calculateWrongX()
        var wrongAnswer3 = calculateWrongX()
        while (wrongAnswer1 == wrongAnswer2) {
            wrongAnswer1 = calculateWrongX()
        }
        while (wrongAnswer3 == wrongAnswer1 || wrongAnswer3 == wrongAnswer2) {
            wrongAnswer3 = calculateWrongX()
        }
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, expression, answers, correctAnswerIndex)
    }

}