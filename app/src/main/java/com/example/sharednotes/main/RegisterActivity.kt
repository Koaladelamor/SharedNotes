package com.example.sharednotes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharednotes.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    //private lateinit var firebaseAuth: FirebaseAuth

    //private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebaseAuth = Firebase.auth

        binding.registerButton.setOnClickListener {

            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()


            AppManager.firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener {
                    //save username and load main activity
                    val name = username.replace(".", "")
                    AppManager.userEmail = name

                    //add notes path


                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Registered correctly", Toast.LENGTH_SHORT).show()


                }.addOnFailureListener{
                    Toast.makeText(this, "Register Error", Toast.LENGTH_SHORT).show()
                }

        }
    }

    /*public override fun onStart() {
        super.onStart()

        val currentUser = firebaseAuth.currentUser
        if(currentUser == null){
            Toast.makeText(this, "Firebase Error", Toast.LENGTH_SHORT).show()
            firebaseAuth = Firebase.auth
        }
    }*/
}