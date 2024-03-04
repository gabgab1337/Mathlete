package com.example.mathlete

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_layout)
        val centerSceneText: TextView = findViewById(R.id.centerText)
        centerSceneText.text = "WITAJ W MATHLETE!"
        val buttonGenerator: Button = findViewById(R.id.buttonGenerator)
        buttonGenerator.setOnClickListener {

        }
        val buttonLessons: Button = findViewById(R.id.buttonLessons)
        buttonLessons.setOnClickListener {

        }
        val buttonProgress: Button = findViewById(R.id.buttonProgress)
        buttonProgress.setOnClickListener {

        }
    }
}
