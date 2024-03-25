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

                val generatorButtons = buttonIds.map { findViewById<Button>(it) }

                val buttonLessons: Button = findViewById(R.id.buttonLessons)
                buttonLessons.setOnClickListener {
                    centerSceneText.visibility = View.VISIBLE
                    generatorButtons.forEach { it.visibility = View.GONE }
                    centerSceneText.text = "Lekcje tutaj o"
                }

                val buttonProgress: Button = findViewById(R.id.buttonProgress)
                buttonProgress.setOnClickListener {
                    centerSceneText.visibility = View.VISIBLE
                    generatorButtons.forEach { it.visibility = View.GONE }
                    centerSceneText.text = "Progressik"
                }

                val buttonGenerator: Button = findViewById(R.id.buttonGenerator)
                buttonGenerator.setOnClickListener {
                    centerSceneText.visibility = View.GONE
                    generatorButtons.forEach { it.visibility = View.VISIBLE }
                }

                generatorButtons.forEachIndexed { index, button ->
                    button.setOnClickListener {
                        val intent = Intent(this, QuizActivity::class.java)
                        intent.putExtra("generatorType", index)
                        startActivity(intent)
                    }
                }
    }
}