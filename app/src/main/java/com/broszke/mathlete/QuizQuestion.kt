package com.broszke.mathlete

data class QuizQuestion(
    val question: String,
    val expression: String,
    val answers: List<String>,
    val correctAnswer: Int
)