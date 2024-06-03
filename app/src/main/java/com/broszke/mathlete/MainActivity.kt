package com.broszke.mathlete

import android.annotation.SuppressLint
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore
    private lateinit var centerSceneText: TextView
    private val RC_SIGN_IN = 9001
    private var receivedVariable: Int = 0
    private var poprawne: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_layout)
        centerSceneText = findViewById(R.id.centerText)
        centerSceneText.text = "Witaj w Mathlete!"
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        //recieve
        receivedVariable = intent.getIntExtra("correctAnswers", 0)
        poprawne += receivedVariable

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Generator buttony
        val buttonIds = arrayOf(
            R.id.buttonLinearGenerator,
            R.id.buttonQuadraticGenerator,
            R.id.buttonVortex,
            R.id.buttonPerpendicularParallel,
            R.id.buttonMultiplication1,
            R.id.buttonMultiplication2,
        )

        val generatorButtons = buttonIds.map { findViewById<Button>(it) }

        val buttonLessons: Button = findViewById(R.id.buttonLessons)
        buttonLessons.setOnClickListener {
            centerSceneText.visibility = View.VISIBLE
            generatorButtons.forEach { it.visibility = View.GONE }
            centerSceneText.text = "TBD\nLekcje tutaj o"
        }

        val buttonProgress: Button = findViewById(R.id.buttonProgress)
        buttonProgress.setOnClickListener {
            centerSceneText.visibility = View.VISIBLE
            generatorButtons.forEach { it.visibility = View.GONE }
            //received Ans
            centerSceneText.text = "Masz Dobrze : $poprawne\n"
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
            val buttonLogin: Button = findViewById(R.id.buttonLogin)
            buttonLogin.setOnClickListener {
                buttonLogin.visibility = View.GONE
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
        }}

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

                    // LogIn Error
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun firebaseAuthWithGoogle(idToken: String) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // LogIn Git
                        val user = auth.currentUser
                        centerSceneText.text = "Witaj ${user?.displayName}!"
                        // Add data for Firestore user
                        user?.let { addUserToFirestore(it) }
                    } else {
                        // LogIn NGit
                        centerSceneText.text = "Sorki ale nie działa"
                    }
                }
        }

        private fun addUserToFirestore(user: FirebaseUser) {
            val userId = user.uid
            val userRef = db.collection("users").document(userId)

            // check if user exist already
            userRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document.exists()) {
                        // if not exist add
                        val userData = hashMapOf(
                            "user" to auth.currentUser,
                            "completedQuizzes" to 0,
                            "incorrectAnswers" to 0,
                            "correctAnswers" to 0
                        )

                        userRef.set(userData)
                            .addOnSuccessListener {
                                // Data aupdate Git
                            }
                            .addOnFailureListener { e ->
                                // Data update NGit
                            }
                    }
                } else {
                    // Error download document
                }
            }
        }
    }
