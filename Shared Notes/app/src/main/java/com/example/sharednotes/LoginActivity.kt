package com.example.sharednotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharednotes.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            Toast.makeText(this, "user: $username, pass: $password", Toast.LENGTH_SHORT).show()

            if(username == "" && password == ""){
                //login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Incorrect user or password", Toast.LENGTH_SHORT).show()

            }
        }
    }
}