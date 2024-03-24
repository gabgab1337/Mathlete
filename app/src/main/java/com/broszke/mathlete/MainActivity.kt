package com.broszke.mathlete

import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.main_screen_layout)
                val centerSceneText: TextView = findViewById(R.id.centerText)
                centerSceneText.text = "Witaj w Mathlete!"

                val buttonLessons: Button = findViewById(R.id.buttonLessons)
                buttonLessons.setOnClickListener {
                    centerSceneText.text = "Lekcje tutaj o"
                }

                val buttonProgress: Button = findViewById(R.id.buttonProgress)
                buttonProgress.setOnClickListener {
                    centerSceneText.text = "Progressik"
                }

                // Generator buttony
                val buttonIds = arrayOf(
                    R.id.buttonLinearGenerator,
                    R.id.buttonQuadraticGenerator,
                    R.id.button3Generator,
                    R.id.button4Generator,
                    R.id.button5Generator,
                    R.id.button6Generator,
                    R.id.button7Generator,
                    R.id.button8Generator,
                    R.id.button9Generator,
                    R.id.button10Generator,
                    R.id.button11Generator,
                    R.id.button12Generator
                )

                val buttons = buttonIds.map { findViewById<Button>(it) }

                val buttonGenerator: Button = findViewById(R.id.buttonGenerator)
                buttonGenerator.setOnClickListener {
                    centerSceneText.visibility = View.GONE
                    buttons.forEach { it.visibility = View.VISIBLE }
                    //val intent = Intent(this, QuizActivity::class.java)
                    //startActivity(intent)
                }
    }
}