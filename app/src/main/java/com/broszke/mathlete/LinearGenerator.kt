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
        } else if (isPeriodicFraction(result.toString())) {
            getPeriodicPart(result.toInt(), 1)
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
        } else if (isPeriodicFraction(result.toString())) {
            getPeriodicPart(result.toInt(), 1)
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

    private fun isPeriodicFraction(number: String): Boolean {
        val parts = number.split(".")
        if (parts.size != 2) return false

        val decimalPart = parts[1]
        for (length in 1..decimalPart.length / 2) {
            val pattern = decimalPart.substring(0, length)
            var isPeriodic = true
            for (i in length until decimalPart.length step length) {
                if (i + length > decimalPart.length || decimalPart.substring(i, i + length) != pattern) {
                    isPeriodic = false
                    break
                }
            }
            if (isPeriodic) return true
        }
        return false
    }

    private fun getPeriodicPart(numerator: Int, denominator: Int): String {
        val integerPart = numerator / denominator
        var remainder = numerator % denominator
        val decimalPart = StringBuilder()
        val remainders = mutableMapOf<Int, Int>()

        while (remainder != 0 && !remainders.containsKey(remainder)) {
            remainders[remainder] = decimalPart.length
            remainder *= 10
            val decimalPlace = remainder / denominator
            decimalPart.append(decimalPlace)
            remainder %= denominator
        }

        return if (remainder != 0) {
            val begin = remainders[remainder]!!
            decimalPart.insert(begin, "(").append(")").toString()
        } else {
            decimalPart.toString()
        }
    }

}