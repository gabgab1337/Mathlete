package com.broszke.mathlete

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_layout)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

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
        buttonLogin.setOnClickListener {
            signIn()
        }

        val buttonProfile: Button = findViewById(R.id.buttonProfile)
        buttonProfile.setOnClickListener {
            if (auth.currentUser != null) {
                buttonLogin.visibility = View.GONE
            } else {
                buttonLogin.visibility = View.VISIBLE
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Obsłuż błąd logowania
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login git
                    val user = auth.currentUser
                } else {
                    // Login fail
                }
            }
    }
}
