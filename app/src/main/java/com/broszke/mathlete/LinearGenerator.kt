package com.broszke.mathlete
import kotlin.random.Random

data class LinearFunction(val slope: Int = 0, val yIntercept: Int = 0)
class LinearGenerator : QuestionGenerator{
    private var function: LinearFunction
    init{
        function = generateFunction()
    }
    private fun generateFunction(): LinearFunction {
        val random = Random
        val slope = random.nextInt(10) + 1
        val yIntercept = random.nextInt(30) - 15
        return LinearFunction(slope, yIntercept)
    }
    private fun calculateX(equals: Int = 0) : String {
        val result = (equals - function.yIntercept.toFloat()) / function.slope
        return if (result % 1 == 0f) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
    private fun calculateWrongX(equals: Int = 0) : String{
        val random = Random
        var change = 0
        while (change == 0) {
            change = random.nextInt(10) - 10
        }
        val result = calculateX(equals).toFloat() + change
        return if (result % 1 == 0f) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }

    override fun generateQuestion(): QuizQuestion {
        function = generateFunction()
        val question = "Oblicz x dla:\n"
        val random = Random
        val equals = random.nextInt(40) - 20
        var expression: String = when (function.slope) {
            1 -> {
                "x"
            }
            -1 -> {
                "-x"
            }
            else -> {
                "${function.slope}x"
            }
        }
        expression += if (function.yIntercept > 0) {
            " + ${function.yIntercept} = $equals"
        } else if (function.yIntercept < 0) {
            " - ${-function.yIntercept} = $equals"
        }else{
            " = $equals"
        }
        val correctAnswer = calculateX(equals).toString()
        var wrongAnswer1 = calculateWrongX(equals).toString()
        val wrongAnswer2 = calculateWrongX(equals).toString()
        var wrongAnswer3 = calculateWrongX(equals).toString()
        while (wrongAnswer1 == wrongAnswer2) {
            wrongAnswer1 = calculateWrongX(equals).toString()
        }
        while (wrongAnswer3 == wrongAnswer1 || wrongAnswer3 == wrongAnswer2) {
            wrongAnswer3 = calculateWrongX(equals).toString()
        }
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, expression, answers, correctAnswerIndex)
    }

    private fun calculateFraction(a: Int, b: Int): String {
        val divisor = GCD(a, b)
        var nominator = a / divisor
        var denominator = b / divisor
        if (denominator < 0) {
            nominator *= -1
            denominator *= -1
        }
        return if (nominator % denominator == 0) {
            (nominator / denominator).toString()
        } else {
            "\u000crac{$nominator}{$denominator}"
        }
    }

    private fun GCD(a: Int, b: Int): Int {
        return if (b == 0) {
            a
        } else {
            GCD(b, a % b)
        }
    }

}