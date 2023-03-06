package com.example.sharednotes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.sharednotes.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private val notesViewModel: NotesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    //save username and load main activity
                    val name = username.replace(".", "")
                    NotesManager.userEmail = name

                    //add notes path


                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }.addOnFailureListener{
                    Toast.makeText(this, "Register Error", Toast.LENGTH_SHORT).show()
                }

        }
    }
}