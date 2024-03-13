package com.example.mathlete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mathlete.ui.theme.MathleteTheme
import androidx.appcompat.app.AppCompatActivity
import com.example.mathlete.R
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.scene_changer_layout)
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
            }
        }
