package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // If already logged in, skip straight to Home
        if (auth.currentUser != null) {
            goToHome(auth.currentUser!!.email ?: "Farmer")
            return
        }

        val etName     = findViewById<EditText>(R.id.etName)
        val etEmail    = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin   = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // ── LOGIN ──────────────────────────────────────────────────
        btnLogin.setOnClickListener {
            val name  = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass  = etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnLogin.isEnabled = false

            auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    goToHome(name.ifEmpty { email })
                }
                .addOnFailureListener { e ->
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true
                    Toast.makeText(this, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        // ── REGISTER ───────────────────────────────────────────────
        btnRegister.setOnClickListener {
            val name  = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass  = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields to register", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnRegister.isEnabled = false

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Welcome, $name!", Toast.LENGTH_SHORT).show()
                    goToHome(name)
                }
                .addOnFailureListener { e ->
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled = true
                    Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun goToHome(userName: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        startActivity(intent)
        finish() // removes LoginActivity from back stack
    }
}
