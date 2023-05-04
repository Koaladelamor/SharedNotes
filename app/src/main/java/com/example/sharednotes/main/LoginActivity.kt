package com.example.sharednotes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharednotes.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var customToken : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth
        //customToken = firebaseAuth.toString()
        //.setFireBaseInstance(firebaseAuth)

        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            //Toast.makeText(this, "user: $username, pass: $password", Toast.LENGTH_SHORT).show()

            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    //save username and load main activity
                    val name = username.replace(".", "")
                    AppManager.userEmail = name

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

            }.addOnFailureListener{
                    Toast.makeText(this, "Incorrect user or password", Toast.LENGTH_SHORT).show()
                }

            /*customToken?.let {
                firebaseAuth.signInWithCustomToken(it)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }*/

        }




        binding.goToRegisterButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        println(currentUser.toString())
    }
}