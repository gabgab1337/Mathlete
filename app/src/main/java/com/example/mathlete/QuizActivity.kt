package com.example.mathlete

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.graphics.Color
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mathlete.LinearGenerator
import com.example.mathlete.QuestionGenerator
import com.example.mathlete.QuizQuestion

class QuizActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_layout)
        val questionText: TextView = findViewById(R.id.question)
        val questionLeftText: TextView = findViewById(R.id.questionsLeft)
        val quizType = 0 // TODO: Dodać inne typy i obsługę typów quizów

        var correctAnswers = 0
        var questionsLeft = 5

        val generator: QuestionGenerator = LinearGenerator()

        val quizQuestion: QuizQuestion = generator.generateQuestion()

        questionText.text = quizQuestion.question
        questionLeftText.text = "Pozostałe pytania: $questionsLeft"


        val buttonAnswer1: Button = findViewById(R.id.buttonAnswer1)
        buttonAnswer1.text = quizQuestion.answers[0]
        buttonAnswer1.setOnClickListener {
            if (quizQuestion.correctAnswer == 0) {
                correctAnswers++
                buttonAnswer1.setBackgroundColor(Color.GREEN)
            }
            else {
                buttonAnswer1.setBackgroundColor(Color.RED)
            }
            questionsLeft -= 1
        }
        val buttonAnswer2: Button = findViewById(R.id.buttonAnswer2)
        buttonAnswer2.text = quizQuestion.answers[1]
        buttonAnswer2.setOnClickListener {
            if (quizQuestion.correctAnswer == 1) {
                correctAnswers++
                buttonAnswer2.setBackgroundColor(Color.GREEN)
            }
            else {
                buttonAnswer2.setBackgroundColor(Color.RED)
            }
            questionsLeft -= 1
        }
        val buttonAnswer3: Button = findViewById(R.id.buttonAnswer3)
        buttonAnswer3.text = quizQuestion.answers[2]
        buttonAnswer3.setOnClickListener {
            if (quizQuestion.correctAnswer == 2) {
                correctAnswers++
                buttonAnswer3.setBackgroundColor(Color.GREEN)
            }
            else {
                buttonAnswer3.setBackgroundColor(Color.RED)
            }
            questionsLeft -= 1
        }
        val buttonAnswer4: Button = findViewById(R.id.buttonAnswer4)
        buttonAnswer4.text = quizQuestion.answers[3]
        buttonAnswer4.setOnClickListener {
            if (quizQuestion.correctAnswer == 3) {
                correctAnswers++
                buttonAnswer4.setBackgroundColor(Color.GREEN)
            }
            else {
                buttonAnswer4.setBackgroundColor(Color.RED)
            }
            questionsLeft -= 1
        }

    }
}