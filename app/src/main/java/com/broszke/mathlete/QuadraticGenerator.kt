package com.broszke.mathlete
import kotlin.math.sqrt
import kotlin.random.Random
import com.broszke.mathlete.calculateFraction

data class QuadraticFunction(val a: Int = 0, val b: Int = 0, val c: Int = 0)
class QuadraticGenerator : QuestionGenerator{
    private var function: QuadraticFunction
    init{
        function = generateFunction()
    }
    private fun generateFunction(): QuadraticFunction {
        val random = Random
        var a: Int
        var b: Int
        var c: Int
        var delta: Int
        val deltaOption = random.nextInt(7)
        do {
            a = random.nextInt(10) + 1
            b = random.nextInt(20) - 10
            delta = when (deltaOption) {
                0 -> b * b + random.nextInt(1, 10) // delta positive
                1 -> 0 // delta zero
                else -> b * b //delta nehative
            }
            c = (b * b - delta) / (4 * a)
        } while ((b * b - 4 * a * c) != delta || a * c == 0)
        return QuadraticFunction(a, b, c)
    }
    private fun calculateX(equals: Int = 0) : String {
        val a = function.a
        val b = function.b
        val c = function.c - equals
        val delta = b * b - 4 * a * c
        if (delta < 0f) {
            return "\\text{Brak miejsc zerowych}"
        }
        else if (delta == 0) {
            return "x = ${calculateFraction((-b), (2 * a))}"
        }
        val x1 = ((-b + sqrt(delta.toDouble())) / (2 * a)).toString()
        val x2 = ((-b - sqrt(delta.toDouble())) / (2 * a)).toString()
        return "x_{1} = ${calculateFraction((-b + sqrt(delta.toDouble())).toInt(), (2 * a))}   x_{2} = ${calculateFraction((-b + sqrt(delta.toDouble())).toInt(), (2 * a))}"
    }
    private fun calculateWrongX() : String{
        val random = Random
        val result1 = random.nextInt(20) - 10
        val result2 = random.nextInt(20) - 10 + result1
        return "x = $result1 lub x = $result2"
    }

    override fun generateQuestion(): QuizQuestion {
        function = generateFunction()
        val question = "Oblicz miejsca zerowe dla:\n"
        val random = Random
        val equals = random.nextInt(20) - 10
        val expression = "${function.a}{x}^2 + ${function.b}x + ${function.b} = $equals"
        val correctAnswer = calculateX(equals)
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