package com.example.mathlete
import kotlin.random.Random

data class LinearFunction(val slope: Int = 0, val yIntercept: Int = 0)
class LinearGenerator : QuestionGenerator{
    val function: LinearFunction
    init{
        function = generateFunction()
    }
    private fun generateFunction(): LinearFunction {
        val random = Random
        val slope = random.nextInt(10) + 1 // Avoid zero slope
        val yIntercept = random.nextInt(10)
        return LinearFunction(slope, yIntercept)
    }
    private fun calculateX() : Float{
        return -function.yIntercept.toFloat() / function.slope
    }
    private fun calculateWrongX() : Float{
        val random = Random
        var change = 0
        while (change == 0) {
            change = random.nextInt(10) - 5
        }
        return calculateX() + change
    }

    override fun generateQuestion(): QuizQuestion {
        val question = "Oblicz x \n${function.slope}x + ${function.yIntercept} = 0"
        val correctAnswer = calculateX().toString()
        val wrongAnswer1 = calculateWrongX().toString()
        val wrongAnswer2 = calculateWrongX().toString()
        val wrongAnswer3 = calculateWrongX().toString()
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, answers, correctAnswerIndex)
    }

}