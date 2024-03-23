package com.example.mathlete
import kotlin.random.Random

data class SquareFunction(val a: Int = 0, val b: Int = 0, val c: Int = 0)
class SquareGenerator : QuestionGenerator{
    private val function: SquareFunction
    init{
        function = generateFunction()
    }
    private fun generateFunction(): SquareFunction {
        val random = Random
        val a = random.nextInt(10) + 1
        val b = random.nextInt(10) - 5
        val c = random.nextInt(20) - 10
        return SquareFunction(a, b, c)
    }
    private fun calculateX(equals: Int = 0) : String {
        val a = function.a.toFloat()
        val b = function.b.toFloat()
        val c = function.c.toFloat() - equals
        val delta = b * b - 4 * a * c
        if (delta < 0f) {
            return "Brak miejsc zerowych"
        }
        else if (delta == 0f) {
            val x = (-b / (2 * a)).toString()
            return "x = $x"
        }
        val x1 = ((-b + Math.sqrt(delta.toDouble())) / (2 * a)).toString()
        val x2 = ((-b - Math.sqrt(delta.toDouble())) / (2 * a)).toString()
        return "x = $x1 lub x = $x2"
    }
    private fun calculateWrongX() : String{
        val random = Random
        val result1 = random.nextInt(20) - 10
        val result2 = random.nextInt(20) - 10 + result1
        return "x = $result1 lub x = $result2"
    }

    override fun generateQuestion(): QuizQuestion {
        val question = "Oblicz miejsca zerowe dla:\n"
        val random = Random
        val equals = random.nextInt(20) - 10
        val expression = "${function.a}x/{2) + ${function.b}x + ${function.b} = $equals"
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