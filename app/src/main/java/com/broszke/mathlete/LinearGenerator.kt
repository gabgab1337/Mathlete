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
        return calculateFraction(equals - function.yIntercept, function.slope)
    }
    private fun calculateWrongX(equals: Int = 0) : String{
        val random = Random
        var change = 0
        while (change == 0) {
            change = random.nextInt(10) - 10
        }
        return calculateFraction((equals - function.yIntercept + change), function.slope)
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
        val correctAnswer = calculateX(equals)
        var wrongAnswer1 = calculateWrongX(equals)
        val wrongAnswer2 = calculateWrongX(equals)
        var wrongAnswer3 = calculateWrongX(equals)
        while (wrongAnswer1 == wrongAnswer2) {
            wrongAnswer1 = calculateWrongX(equals)
        }
        while (wrongAnswer3 == wrongAnswer1 || wrongAnswer3 == wrongAnswer2) {
            wrongAnswer3 = calculateWrongX(equals)
        }
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, expression, answers, correctAnswerIndex)
    }

    private fun calculateFraction(a: Int, b: Int): String {
        if (b == 0) {
            throw IllegalArgumentException("Denominator cannot be zero.")
        }
        var sign = ""
        var nominator = a
        var denominator = b
        if (denominator < 0) {
            nominator *= -1
            denominator *= -1
        }
        if (nominator < 0) {
            nominator *= -1
            sign += "-"
        }
        return if (nominator % denominator == 0) {
            sign + (nominator / denominator).toString()
        } else {
            val divisor = gcd(nominator, denominator)
            nominator /= divisor
            denominator /= divisor
            formatFraction(sign, nominator, denominator)
        }
    }

    private fun formatFraction(sign: String, nominator: Int, denominator: Int): String {
        return if (nominator % denominator == 0) {
            "$sign${nominator / denominator}"
        } else if (nominator / denominator == 0) {
            "$sign\\frac{$nominator}{$denominator}"
        } else {
            "$sign${nominator/denominator}\\frac{${nominator % denominator}}{$denominator}"
        }
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) {
            a
        } else {
            gcd(b, a % b)
        }
    }

}