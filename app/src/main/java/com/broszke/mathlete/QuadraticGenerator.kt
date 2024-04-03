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
        do {
            a = random.nextInt(10) + 1
            b = random.nextInt(20) - 10
            val n = random.nextInt(10) + 1 // generate a random integer n
            delta = n * n // delta is a perfect square
            c = (b * b - delta) / (4 * a)
        } while (a * c == 0)
        return QuadraticFunction(a, b, c, delta)
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
        val question = "Oblicz miejsca zerowe dla:\n"
        val expression = "${function.a}{x}^2+${function.b}x+${function.c}=0"
        //expression = expression.replace("+-", "-")
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