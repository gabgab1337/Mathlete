package com.example.mathlete

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.graphics.Color
import android.os.Looper
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mathlete.LinearGenerator
import com.example.mathlete.QuestionGenerator
import com.example.mathlete.QuizQuestion

class QuizActivity : AppCompatActivity() {
    private lateinit var buttonAnswer1: Button
    private lateinit var buttonAnswer2: Button
    private lateinit var buttonAnswer3: Button
    private lateinit var buttonAnswer4: Button
    private lateinit var questionText: TextView
    private lateinit var questionLeftText: TextView
    private var questionsLeft = 5
    private val generator: QuestionGenerator = LinearGenerator() // TODO: Dodać inne generatory
    private var correctAnswers = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_layout)

        buttonAnswer1 = findViewById(R.id.buttonAnswer1)
        buttonAnswer2 = findViewById(R.id.buttonAnswer2)
        buttonAnswer3 = findViewById(R.id.buttonAnswer3)
        buttonAnswer4 = findViewById(R.id.buttonAnswer4)
        questionText = findViewById(R.id.question)
        questionLeftText = findViewById(R.id.questionsLeft)

        val quizType = 0 // TODO: Dodać inne typy i obsługę typów quizów
        val quizQuestion: QuizQuestion = generator.generateQuestion()

        questionText.text = quizQuestion.question
        questionLeftText.text = "Pozostałe pytania: $questionsLeft"

        buttonAnswer1.text = quizQuestion.answers[0]
        buttonAnswer1.setOnClickListener {
            buttonAnswer1.isEnabled = false
            buttonAnswer2.isEnabled = false
            buttonAnswer3.isEnabled = false
            buttonAnswer4.isEnabled = false

            if (quizQuestion.correctAnswer == 0) {
                correctAnswers++
                buttonAnswer1.setBackgroundColor(Color.GREEN)
            } else {
                buttonAnswer1.setBackgroundColor(Color.RED)
                when (quizQuestion.correctAnswer) {
                    1 -> buttonAnswer2.setBackgroundColor(Color.GREEN)
                    2 -> buttonAnswer3.setBackgroundColor(Color.GREEN)
                    3 -> buttonAnswer4.setBackgroundColor(Color.GREEN)
                }
            }
            questionsLeft -= 1

            Handler(Looper.getMainLooper()).postDelayed({
                generateNewQuestion()
                buttonAnswer1.isEnabled = true
                buttonAnswer2.isEnabled = true
                buttonAnswer3.isEnabled = true
                buttonAnswer4.isEnabled = true
            }, 5000)
        }

        buttonAnswer2.text = quizQuestion.answers[1]
        buttonAnswer2.setOnClickListener {
            buttonAnswer1.isEnabled = false
            buttonAnswer2.isEnabled = false
            buttonAnswer3.isEnabled = false
            buttonAnswer4.isEnabled = false

            if (quizQuestion.correctAnswer == 1) {
                correctAnswers++
                buttonAnswer2.setBackgroundColor(Color.GREEN)
            } else {
                buttonAnswer2.setBackgroundColor(Color.RED)
                when (quizQuestion.correctAnswer) {
                    0 -> buttonAnswer1.setBackgroundColor(Color.GREEN)
                    2 -> buttonAnswer3.setBackgroundColor(Color.GREEN)
                    3 -> buttonAnswer4.setBackgroundColor(Color.GREEN)
                }
            }
            questionsLeft -= 1

            Handler(Looper.getMainLooper()).postDelayed({
                generateNewQuestion()
                buttonAnswer1.isEnabled = true
                buttonAnswer2.isEnabled = true
                buttonAnswer3.isEnabled = true
                buttonAnswer4.isEnabled = true
            }, 5000)
        }

        buttonAnswer3.text = quizQuestion.answers[2]
        buttonAnswer3.setOnClickListener {
            buttonAnswer1.isEnabled = false
            buttonAnswer2.isEnabled = false
            buttonAnswer3.isEnabled = false
            buttonAnswer4.isEnabled = false

            if (quizQuestion.correctAnswer == 2) {
                correctAnswers++
                buttonAnswer1.setBackgroundColor(Color.GREEN)
            } else {
                buttonAnswer1.setBackgroundColor(Color.RED)
                when (quizQuestion.correctAnswer) {
                    0 -> buttonAnswer1.setBackgroundColor(Color.GREEN)
                    1 -> buttonAnswer2.setBackgroundColor(Color.GREEN)
                    3 -> buttonAnswer4.setBackgroundColor(Color.GREEN)
                }
            }
            questionsLeft -= 1

            Handler(Looper.getMainLooper()).postDelayed({
                generateNewQuestion()
                buttonAnswer1.isEnabled = true
                buttonAnswer2.isEnabled = true
                buttonAnswer3.isEnabled = true
                buttonAnswer4.isEnabled = true
            }, 5000)
        }

        buttonAnswer4.text = quizQuestion.answers[3]
        buttonAnswer4.setOnClickListener {
            buttonAnswer1.isEnabled = false
            buttonAnswer2.isEnabled = false
            buttonAnswer3.isEnabled = false
            buttonAnswer4.isEnabled = false

            if (quizQuestion.correctAnswer == 3) {
                correctAnswers++
                buttonAnswer4.setBackgroundColor(Color.GREEN)
            } else {
                buttonAnswer4.setBackgroundColor(Color.RED)
                when (quizQuestion.correctAnswer) {
                    0 -> buttonAnswer1.setBackgroundColor(Color.GREEN)
                    1 -> buttonAnswer2.setBackgroundColor(Color.GREEN)
                    2 -> buttonAnswer3.setBackgroundColor(Color.GREEN)
                }
            }
            questionsLeft -= 1

            Handler(Looper.getMainLooper()).postDelayed({
                generateNewQuestion()
                buttonAnswer1.isEnabled = true
                buttonAnswer2.isEnabled = true
                buttonAnswer3.isEnabled = true
                buttonAnswer4.isEnabled = true
            }, 5000)
        }

        // TODO: Zakańczanie quizu
    }


    @SuppressLint("SetTextI18n")
    private fun generateNewQuestion() {
        if (questionsLeft > 0) {
            val generator: QuestionGenerator = LinearGenerator() // TODO: Dodać inne generatory
            val quizQuestion: QuizQuestion = generator.generateQuestion()

            questionText.text = quizQuestion.question
            questionLeftText.text = "Pozostałe pytania: $questionsLeft"

            buttonAnswer1.text = quizQuestion.answers[0]
            buttonAnswer2.text = quizQuestion.answers[1]
            buttonAnswer3.text = quizQuestion.answers[2]
            buttonAnswer4.text = quizQuestion.answers[3]

            buttonAnswer1.setBackgroundColor(Color.LTGRAY)
            buttonAnswer2.setBackgroundColor(Color.LTGRAY)
            buttonAnswer3.setBackgroundColor(Color.LTGRAY)
            buttonAnswer4.setBackgroundColor(Color.LTGRAY)
        } else {
            questionText.text = "Koniec! Poprawne odpowiedzi: $correctAnswers."
            buttonAnswer1.isEnabled = false
            buttonAnswer2.isEnabled = false
            buttonAnswer3.isEnabled = false
            buttonAnswer4.isEnabled = false
        }
    }
}