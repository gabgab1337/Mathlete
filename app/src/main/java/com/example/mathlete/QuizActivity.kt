package com.example.mathlete

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.graphics.Color
import android.os.CountDownTimer
import android.os.Looper
import android.os.Handler
import android.webkit.WebView
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
    private lateinit var timer: CountDownTimer
    private lateinit var timerTextView: TextView
    private lateinit var expressionView: WebView

    private var questionsLeft = 5
    private val generator: QuestionGenerator = LinearGenerator() // TODO: Dodać inne generatory
    private var quizQuestion: QuizQuestion = generator.generateQuestion()
    private var correctAnswers = 0

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.quiz_layout)
        val quizType = 0 // TODO: Dodać inne typy i obsługę typów quizów
        enableEdgeToEdge()

        // UI elements
        buttonAnswer1 = findViewById(R.id.buttonAnswer1)
        buttonAnswer2 = findViewById(R.id.buttonAnswer2)
        buttonAnswer3 = findViewById(R.id.buttonAnswer3)
        buttonAnswer4 = findViewById(R.id.buttonAnswer4)
        questionText = findViewById(R.id.question)
        questionLeftText = findViewById(R.id.questionsLeft)
        timerTextView = findViewById(R.id.timer)
        expressionView = findViewById(R.id.expression)
        expressionView.settings.javaScriptEnabled = true
        expressionView.setBackgroundColor(Color.TRANSPARENT)
        expressionView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        loadExpression()

        questionText.text = quizQuestion.question
        questionLeftText.text = "Pozostałe pytania: $questionsLeft"

        // Buttony i handlery
        buttonAnswer1.text = quizQuestion.answers[0]
        buttonAnswer2.text = quizQuestion.answers[1]
        buttonAnswer3.text = quizQuestion.answers[2]
        buttonAnswer4.text = quizQuestion.answers[3]
        handleButtonClick(buttonAnswer1, 0)
        handleButtonClick(buttonAnswer2, 1)
        handleButtonClick(buttonAnswer3, 2)
        handleButtonClick(buttonAnswer4, 3)

        // Timer
        timer = object : CountDownTimer(300000, 1000) { // 300000 milliseconds = 5 minutes
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                questionText.text = "Koniec czsu!\n Poprawne odpowiedzi: $correctAnswers."
                disableButtons()
            }
        }.start()

        val closeButton: TextView = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            finish()
        }
    }

    private fun handleButtonClick(button: Button, answerIndex: Int) {
        button.setOnClickListener {
            disableButtons()

            if (quizQuestion.correctAnswer == answerIndex) {
                correctAnswers++
                button.setBackgroundResource(R.drawable.button_correct_background)
            } else {
                button.setBackgroundResource(R.drawable.button_wrong_background)
                when (quizQuestion.correctAnswer) {
                    0 -> buttonAnswer1.setBackgroundResource(R.drawable.button_correct_background)
                    1 -> buttonAnswer2.setBackgroundResource(R.drawable.button_correct_background)
                    2 -> buttonAnswer3.setBackgroundResource(R.drawable.button_correct_background)
                    3 -> buttonAnswer4.setBackgroundResource(R.drawable.button_correct_background)
                }
            }
            questionsLeft -= 1

            Handler(Looper.getMainLooper()).postDelayed({
                generateNewQuestion()
                enableButtons()
            }, 3000)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun generateNewQuestion() {
        if (questionsLeft > 0) {
            val generator: QuestionGenerator = LinearGenerator()
            quizQuestion = generator.generateQuestion()

            questionLeftText.text = "Pozostałe pytania: $questionsLeft"
            questionText.text = quizQuestion.question

            loadExpression()

            buttonAnswer1.text = quizQuestion.answers[0]
            buttonAnswer2.text = quizQuestion.answers[1]
            buttonAnswer3.text = quizQuestion.answers[2]
            buttonAnswer4.text = quizQuestion.answers[3]

            buttonAnswer1.setBackgroundResource(R.drawable.button_background)
            buttonAnswer2.setBackgroundResource(R.drawable.button_background)
            buttonAnswer3.setBackgroundResource(R.drawable.button_background)
            buttonAnswer4.setBackgroundResource(R.drawable.button_background)
        } else {
            timer.cancel()
            questionText.text = "Koniec! Poprawne odpowiedzi: $correctAnswers."
            disableButtons()
        }
    }

    private fun disableButtons() {
        buttonAnswer1.isEnabled = false
        buttonAnswer2.isEnabled = false
        buttonAnswer3.isEnabled = false
        buttonAnswer4.isEnabled = false
    }

    private fun enableButtons() {
        buttonAnswer1.isEnabled = true
        buttonAnswer2.isEnabled = true
        buttonAnswer3.isEnabled = true
        buttonAnswer4.isEnabled = true
    }

    private fun loadExpression(){
        val html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <script type="text/javascript" async
                        src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.7/MathJax.js?config=TeX-MML-AM_CHTML">
                    </script>
                    <style>
                        body{
                            display: flex;
                            justify-content: center;
     
                            font-size: 32px;
                        }
                    </style>
                </head>
                <body>
                    <script type="text/javascipt">
                        MathJax.Hub.Config({
                            tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}
                        });
                    </script>
                    <p>
                        $$${quizQuestion.expression}$$
                    </p>
                </body>
                </html>
            """.trimIndent() // TODO: Pobrać bibliotekę MathJax do projektu żeby to się szybciej ładowało
        expressionView.loadDataWithBaseURL("https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.7/" ,html, "text/html", "utf-8", null)
    }
}