package com.example.mathlete

data class QuizQuestion(
    val question: String,
    val expression: String,
    val answers: List<String>,
    val correctAnswer: Int
)