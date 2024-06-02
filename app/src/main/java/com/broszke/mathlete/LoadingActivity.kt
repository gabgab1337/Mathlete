package com.broszke.mathlete

import android.animation.Animator
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.broszke.mathlete.R

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_layout)

        val animationView = findViewById<LottieAnimationView>(R.id.animation_view)
        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Implementation for onAnimationStart
            }

            override fun onAnimationEnd(animation: Animator) {
                setResult(Activity.RESULT_OK)
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {
                // Implementation for onAnimationCancel
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Implementation for onAnimationRepeat
            }
        })
    }
}