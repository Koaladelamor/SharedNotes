package com.example.sharednotes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharednotes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            //Toast.makeText(this, "user: $username, pass: $password", Toast.LENGTH_SHORT).show()

            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    //login
                    val intent = Intent(this, MainActivity::class.java)
//                    intent.putExtra()
                    startActivity(intent)
            }.addOnFailureListener{
                    Toast.makeText(this, "Incorrect user or password", Toast.LENGTH_SHORT).show()
                }

        }
    }
}