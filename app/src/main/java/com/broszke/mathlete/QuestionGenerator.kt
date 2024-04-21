package com.broszke.mathlete

interface QuestionGenerator {
    fun generateQuestionX(): QuizQuestion {
        throw UnsupportedOperationException("Not implemented")
    }
    fun generateQuestionVertex(): QuizQuestion {
        throw UnsupportedOperationException("Not implemented")
    }
    fun generateQuestionPP(): QuizQuestion {
        throw UnsupportedOperationException("Not implemented")
    }
    fun generateQuestionMultiplication1(): QuizQuestion {
        throw UnsupportedOperationException("Not implemented")
    }
    fun generateQuestionMultiplication2(): QuizQuestion {
        throw UnsupportedOperationException("Not implemented")
    }
}