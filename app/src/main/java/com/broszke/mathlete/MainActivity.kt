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
        val buttonGenerator: Button = findViewById(R.id.buttonGenerator)
        buttonGenerator.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
        val buttonLessons: Button = findViewById(R.id.buttonLessons)
        buttonLessons.setOnClickListener {
            centerSceneText.text = "Lekcje tutaj o"
        }
        val buttonProgress: Button = findViewById(R.id.buttonProgress)
        buttonProgress.setOnClickListener {
            centerSceneText.text = "Progressik"
        }
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val buttonProfile: Button = findViewById(R.id.buttonProfile)

        buttonProfile.setOnClickListener{
            buttonLogin.visibility = View.VISIBLE
        }

    }
        }
