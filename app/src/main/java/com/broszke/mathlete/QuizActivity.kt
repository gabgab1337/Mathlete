package com.broszke.mathlete

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.graphics.Color
import android.os.CountDownTimer
import android.os.Looper
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    private lateinit var buttonAnswer1: WebView
    private lateinit var buttonAnswer2: WebView
    private lateinit var buttonAnswer3: WebView
    private lateinit var buttonAnswer4: WebView
    private lateinit var buttonExit: Button
    private lateinit var questionText: TextView
    private lateinit var questionLeftText: TextView
    private lateinit var timer: CountDownTimer
    private lateinit var timerTextView: TextView
    private lateinit var expressionView: WebView

    private var generatorType: Int = 0
    private lateinit var generatorsArray: Array<QuestionGenerator>
    private lateinit var generator: QuestionGenerator
    private lateinit var quizQuestion: QuizQuestion
    private var questionsLeft = 5
    private var correctAnswers = 0

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.quiz_layout)
        enableEdgeToEdge()

        generatorType = intent.getIntExtra("generatorType", 0)
        generatorsArray = arrayOf(
            LinearGenerator(),
            QuadraticGenerator(),
            QuadraticGenerator())
        generator = generatorsArray[generatorType] // TODO: Dodać inne generatory
        quizQuestion = if (generatorType == 2){
            generator.generateQuestionVertex()
        } else{
            generator.generateQuestionX()
        }

        // UI elements
        buttonAnswer1 = findViewById<FrameLayout>(R.id.buttonAnswer1).findViewById(R.id.webViewButton)
        buttonAnswer2 = findViewById<FrameLayout>(R.id.buttonAnswer2).findViewById(R.id.webViewButton)
        buttonAnswer3 = findViewById<FrameLayout>(R.id.buttonAnswer3).findViewById(R.id.webViewButton)
        buttonAnswer4 = findViewById<FrameLayout>(R.id.buttonAnswer4).findViewById(R.id.webViewButton)
        buttonExit = findViewById(R.id.buttonExit)
        questionText = findViewById(R.id.question)
        questionLeftText = findViewById(R.id.questionsLeft)
        timerTextView = findViewById(R.id.timer)
        expressionView = findViewById(R.id.expression)
        expressionView.settings.javaScriptEnabled = true
        expressionView.setBackgroundColor(Color.TRANSPARENT)
        expressionView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        loadExpression()

        // Disable WebView's built-in click handling
        buttonAnswer1.isClickable = false
        buttonAnswer1.isFocusable = false
        buttonAnswer2.isClickable = false
        buttonAnswer2.isFocusable = false
        buttonAnswer3.isClickable = false
        buttonAnswer3.isFocusable = false
        buttonAnswer4.isClickable = false
        buttonAnswer4.isFocusable = false

        questionText.text = quizQuestion.question
        questionLeftText.text = "Pozostałe pytania: $questionsLeft"

        // Buttony i handlery
        loadAnswerIntoWebView(buttonAnswer1, quizQuestion.answers[0])
        loadAnswerIntoWebView(buttonAnswer2, quizQuestion.answers[1])
        loadAnswerIntoWebView(buttonAnswer3, quizQuestion.answers[2])
        loadAnswerIntoWebView(buttonAnswer4, quizQuestion.answers[3])
        handleWebViewClick(buttonAnswer1, 0)
        handleWebViewClick(buttonAnswer2, 1)
        handleWebViewClick(buttonAnswer3, 2)
        handleWebViewClick(buttonAnswer4, 3)

        // Timer
        timer = object : CountDownTimer(300000, 1000) { // 300000 milliseconds = 5 minutes
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                questionText.text = "\n\n\n\nKoniec czasu!\n Poprawne odpowiedzi: $correctAnswers."
                endQuiz()
            }
        }.start()

        buttonExit.setOnClickListener {
            finish()
        }

        val closeButton: TextView = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleWebViewClick(webView: WebView, answerIndex: Int) {
        webView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                v.performClick() // Call performClick when a click is detected

                disableWebViews()

                if (quizQuestion.correctAnswer == answerIndex) {
                    correctAnswers++
                    webView.setBackgroundResource(R.drawable.button_correct_background)
                } else {
                    webView.setBackgroundResource(R.drawable.button_wrong_background)
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
                    enableWebViews()
                }, 3000)
            }
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun generateNewQuestion() {
        if (questionsLeft > 0) {
            val generator: QuestionGenerator = generatorsArray[generatorType]
            quizQuestion = generator.generateQuestionX()

            questionLeftText.text = "Pozostałe pytania: $questionsLeft"
            questionText.text = quizQuestion.question

            loadExpression()

            loadAnswerIntoWebView(buttonAnswer1, quizQuestion.answers[0])
            loadAnswerIntoWebView(buttonAnswer2, quizQuestion.answers[1])
            loadAnswerIntoWebView(buttonAnswer3, quizQuestion.answers[2])
            loadAnswerIntoWebView(buttonAnswer4, quizQuestion.answers[3])

            buttonAnswer1.setBackgroundResource(R.drawable.button_background)
            buttonAnswer2.setBackgroundResource(R.drawable.button_background)
            buttonAnswer3.setBackgroundResource(R.drawable.button_background)
            buttonAnswer4.setBackgroundResource(R.drawable.button_background)
        } else {
            timer.cancel()
            questionText.text = "\n\n\n\nKoniec!\nPoprawne odpowiedzi: $correctAnswers."
            endQuiz()
        }
    }

    private fun disableWebViews() {
        buttonAnswer1.isEnabled = false
        buttonAnswer2.isEnabled = false
        buttonAnswer3.isEnabled = false
        buttonAnswer4.isEnabled = false
    }

    private fun enableWebViews() {
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
                    src="file:///android_asset/MathJax/es5/tex-chtml-full.js">
                </script>
                <script type="text/javascript">
                    MathJax = {
                        tex: {
                            inlineMath: [['$', '$'], ['\\(', '\\)']]
                        },
                        options: {
                            skipHtmlTags: ['script', 'noscript', 'style', 'textarea', 'pre']
                        },
                        startup: {
                            ready: () => {
                                MathJax.startup.defaultReady();
                                MathJax.startup.promise.then(() => {
                                    console.log('MathJax initial typesetting complete');
                                });
                            },
                            displayErrors: true,
                            displayMessages: true
                        }
                    };
                </script>
                <style>
                    body{
                        display: flex;
                        justify-content: center;

                        font-size: 28px;
                    }
                </style>
            </head>
            <body>
                <p>
                    $$${quizQuestion.expression}$$
                </p>
            </body>
            </html>
        """.trimIndent()
        expressionView.loadDataWithBaseURL("file:///android_asset/" ,html, "text/html", "utf-8", null)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadAnswerIntoWebView(webView: WebView, answer: String) {
        webView.settings.javaScriptEnabled = true
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
                <script type="text/javascript" async
                    src="file:///android_asset/MathJax/es5/tex-chtml-full.js">
                </script>
                <script type="text/javascript">
                    MathJax = {
                        tex: {
                            inlineMath: [['$', '$'], ['\\(', '\\)']]
                        },
                        options: {
                            skipHtmlTags: ['script', 'noscript', 'style', 'textarea', 'pre']
                        },
                        startup: {
                            ready: () => {
                                MathJax.startup.defaultReady();
                                MathJax.startup.promise.then(() => {
                                    console.log('MathJax initial typesetting complete');
                                });
                            },
                            displayErrors: true,
                            displayMessages: true
                        }
                    };
                </script>
                <style>
                    body{
                        display: flex;
                        justify-content: center;
                        font-size: 14px;
                    }
                </style>
            </head>
            <body>
                <p>
                    $$${answer}$$
                </p>
            </body>
            </html>
        """.trimIndent()
        webView.loadDataWithBaseURL("file:///android_asset/" ,html, "text/html", "utf-8", null)
    }

    private fun endQuiz() {
        // Hide answer buttons
        buttonAnswer1.visibility = View.GONE
        buttonAnswer2.visibility = View.GONE
        buttonAnswer3.visibility = View.GONE
        buttonAnswer4.visibility = View.GONE
        questionLeftText.visibility = View.GONE
        expressionView.visibility = View.GONE

        // Show restart and exit buttons
        buttonExit.visibility = View.VISIBLE
    }
}