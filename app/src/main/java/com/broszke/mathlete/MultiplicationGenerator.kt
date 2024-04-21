package com.broszke.mathlete
import kotlin.random.Random
import com.broszke.mathlete.calculateFraction

data class Multiplication(val a: Int = 0, val b: Int = 0)
class MultiplicationGenerator : QuestionGenerator{
    private var equasion: Multiplication
    init{
        equasion = generateMultiplication()
    }
    private fun generateMultiplication(): Multiplication {
        val random = Random
        val a = random.nextInt(10) + 1
        val b = random.nextInt(20) - 10
        return Multiplication(a, b)
    }

    override fun generateQuestionMultiplication1(): QuizQuestion {
        equasion = generateMultiplication()
        val question = "Ile wynosi:"
        var expression = "(${equasion.a}x"
        expression += if (equasion.b < 0) {
            " ${equasion.b})^{2}"
        } else {
            " + ${equasion.b})^{2}"
        }
        var correctAnswer = "${equasion.a * equasion.a}x^{2} + ${2 * equasion.a * equasion.b}x + ${equasion.b * equasion.b}"
        var wrongAnswer1 = "${equasion.a * equasion.a}x^{2} + ${2 * equasion.a * equasion.b}x - ${equasion.b * equasion.b}"
        var wrongAnswer2 = "${equasion.a}x^{2} + ${equasion.a * equasion.b}x - ${equasion.b * equasion.b}"
        var wrongAnswer3 = "${equasion.a * equasion.a}x^{2} + ${2 * equasion.a * equasion.b}x + ${equasion.b * equasion.b + 1}"
        if (equasion.b < 0) {
            correctAnswer = "${equasion.a * equasion.a}x^{2} ${2 * equasion.a * equasion.b}x + ${equasion.b * equasion.b}"
            wrongAnswer1 = "${equasion.a * equasion.a}x^{2} ${2 * equasion.a * equasion.b}x - ${equasion.b * equasion.b}"
            wrongAnswer2 = "${equasion.a}x^{2} ${equasion.a * equasion.b}x - ${equasion.b * equasion.b}"
            wrongAnswer3 = "${equasion.a * equasion.a}x^{2} ${2 * equasion.a * equasion.b}x + ${equasion.b * equasion.b + 1}"
        }
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, expression, answers, correctAnswerIndex)
    }

    override fun generateQuestionMultiplication2(): QuizQuestion {
        equasion = generateMultiplication()
        val question = "Ile wynosi:"
        var expression = "(${equasion.a}x"
        expression += if (equasion.b < 0) {
            " ${equasion.b})^{3}"
        } else {
            " + ${equasion.b})^{3}"
        }
        var correctAnswer = "${equasion.a * equasion.a * equasion.a}x^{3} + ${3 * equasion.a * equasion.a * equasion.b}x^{2} + ${3 * equasion.a * equasion.b * equasion.b}x + ${equasion.b * equasion.b * equasion.b}"
        var wrongAnswer1 = "${equasion.a * equasion.a * equasion.a}x^{3} + ${3 * equasion.a * equasion.a * equasion.b}x^{2} + ${3 * equasion.a * equasion.b * equasion.b}x - ${equasion.b * equasion.b * equasion.b}"
        var wrongAnswer2 = "${equasion.a * equasion.a * equasion.a}x^{3} + ${3 * equasion.a * equasion.a * equasion.b}x^{2} + ${3 * equasion.a * equasion.b}x + ${equasion.b * equasion.b}"
        var wrongAnswer3 = "${equasion.a * equasion.a}x^{2} + ${2 * equasion.a * equasion.b}x + ${equasion.b * equasion.b}"
        if (equasion.b < 0){
            correctAnswer = "${equasion.a * equasion.a * equasion.a}x^{3} ${3 * equasion.a * equasion.a * equasion.b}x^{2} + ${3 * equasion.a * equasion.b * equasion.b}x ${equasion.b * equasion.b * equasion.b}"
            wrongAnswer1 = "${equasion.a * equasion.a * equasion.a}x^{3} ${3 * equasion.a * equasion.a * equasion.b}x^{2} + ${3 * equasion.a * equasion.b * equasion.b}x ${equasion.b * equasion.b * equasion.b}"
            wrongAnswer2 = "${equasion.a * equasion.a * equasion.a}x^{3} ${3 * equasion.a * equasion.a * equasion.b}x^{2} ${3 * equasion.a * equasion.b}x - ${equasion.b * equasion.b}"
            wrongAnswer3 = "${equasion.a * equasion.a}x^{2} ${2 * equasion.a * equasion.b}x + ${equasion.b * equasion.b}"
        }
        val answers = listOf(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
        val correctAnswerIndex = answers.indexOf(correctAnswer)
        return QuizQuestion(question, expression, answers, correctAnswerIndex)
    }
}