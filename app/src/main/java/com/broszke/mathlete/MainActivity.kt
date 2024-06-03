package com.broszke.mathlete

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseUser
import android.util.Log
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore
    private lateinit var centerSceneText: TextView
    private lateinit var progressText: TextView
    private val RC_SIGN_IN = 9001
    private var receivedVariable: Int = 0
    private var badAns: Int = 0
    private var quizCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_layout)
        centerSceneText = findViewById(R.id.centerText)
        progressText = findViewById(R.id.progressText)
        centerSceneText.text = "Witaj w Mathlete!"
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        if (auth.currentUser != null) {
            fetchUserData(auth.currentUser!!)
        }

        // Odbieranie danych z intencji
        val completedQuizzes = intent.getIntExtra("completedQuizzes", 0)
        val correctAnswers = intent.getIntExtra("correctAnswers", 0)
        val incorrectAnswers = intent.getIntExtra("incorrectAnswers", 0)

        if (completedQuizzes > 0 || correctAnswers > 0 || incorrectAnswers > 0) {
            auth.currentUser?.let { user ->
                updateUserStats(user, completedQuizzes, correctAnswers, incorrectAnswers)
            }
        }

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
        val buttonLogin: Button = findViewById(R.id.buttonLogin)

        val buttonLessons: Button = findViewById(R.id.buttonLessons)
        buttonLessons.setOnClickListener {
            centerSceneText.visibility = View.VISIBLE
            progressText.visibility = View.GONE
            buttonLogin.visibility = View.GONE
            generatorButtons.forEach { it.visibility = View.GONE }
            centerSceneText.text = "Rób quizy! ;)"
        }

        val buttonProgress: Button = findViewById(R.id.buttonProgress)
        buttonProgress.setOnClickListener {
            centerSceneText.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
            buttonLogin.visibility = View.GONE
            if (auth.currentUser != null) {
                fetchUserData(auth.currentUser!!)
            }
            auth.currentUser?.let { user ->
                updateUserStats(user, completedQuizzes, correctAnswers, incorrectAnswers)
            }
            generatorButtons.forEach { it.visibility = View.GONE }
            progressText.text = "Twoja skuteczność: ${round(receivedVariable.toDouble() / (badAns.toDouble() + receivedVariable) * 100).toInt()}%"
            centerSceneText.text = "Zrobiłes $quizCount quizów, gratulacje :)\nPoprawne odpowiedzi: $receivedVariable\nBłędne odpowiedzi: $badAns"
        }

        val buttonGenerator: Button = findViewById(R.id.buttonGenerator)
        buttonGenerator.setOnClickListener {
            centerSceneText.visibility = View.GONE
            progressText.visibility = View.GONE
            buttonLogin.visibility = View.GONE
            generatorButtons.forEach { it.visibility = View.VISIBLE }
        }

        generatorButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("generatorType", index)
                startActivity(intent)
            }
        }

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

        // Check if user exists already
        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.exists()) {
                    // If not exist, add
                    val userData = hashMapOf(
                        "uid" to userId,
                        "name" to user.displayName,
                        "email" to user.email,
                        "completedQuizzes" to 0,
                        "incorrectAnswers" to 0,
                        "correctAnswers" to 0
                    )

                    userRef.set(userData)
                        .addOnSuccessListener {
                            // Data update successful
                            Log.d("Firestore", "User data successfully written!")
                        }
                        .addOnFailureListener { e ->
                            // Data update failed
                            Log.w("Firestore", "Error writing user data", e)
                        }
                }
            } else {
                // Error getting document
                Log.w("Firestore", "Error getting document", task.exception)
            }
        }
    }

    private fun updateUserStats(user: FirebaseUser, completedQuizzes: Int, correctAnswers: Int, incorrectAnswers: Int) {
        val userId = user.uid
        val userRef = db.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val currentCompletedQuizzes = document.getLong("completedQuizzes") ?: 0
                val currentCorrectAnswers = document.getLong("correctAnswers") ?: 0
                val currentIncorrectAnswers = document.getLong("incorrectAnswers") ?: 0

                val newCompletedQuizzes = currentCompletedQuizzes + completedQuizzes
                val newCorrectAnswers = currentCorrectAnswers + correctAnswers
                val newIncorrectAnswers = currentIncorrectAnswers + incorrectAnswers

                val updatedData = mapOf(
                    "completedQuizzes" to newCompletedQuizzes,
                    "correctAnswers" to newCorrectAnswers,
                    "incorrectAnswers" to newIncorrectAnswers
                )

                userRef.update(updatedData)
                    .addOnSuccessListener {
                        // Data update successful
                        quizCount = newCompletedQuizzes.toInt()
                        receivedVariable = newCorrectAnswers.toInt()
                        badAns = newIncorrectAnswers.toInt()
                        Log.d("Firestore", "User stats successfully updated!")
                    }
                    .addOnFailureListener { e ->
                        // Data update failed
                        Log.w("Firestore", "Error updating user stats", e)
                    }
            }
        }
    }

    private fun fetchUserData(user: FirebaseUser) {
        val userId = user.uid
        val userRef = db.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val currentCompletedQuizzes = document.getLong("completedQuizzes") ?: 0
                val currentCorrectAnswers = document.getLong("correctAnswers") ?: 0
                val currentIncorrectAnswers = document.getLong("incorrectAnswers") ?: 0

                quizCount = currentCompletedQuizzes.toInt()
                receivedVariable = currentCorrectAnswers.toInt()
                badAns = currentIncorrectAnswers.toInt()

            }
        }
    }
}
