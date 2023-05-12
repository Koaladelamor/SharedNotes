package com.example.sharednotes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharednotes.databinding.ActivityCreateRequestBinding

class CreateRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRequestBinding
    private lateinit var requestsFragment: RequestsFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.requestAddButton.setOnClickListener {
            val newRequest = Note(-1,
                binding.requestTitle.text.toString(),
                binding.requestDescription.text.toString(),
                //false,
                //binding.newNoteReminderDate.text.toString(),
                binding.requestRecipient.text.toString(),
                AppManager.userEmail,
                //"Pending"
            )

            // go to main activity
            //println(newRequest.recipient)
            val responseIntent = Intent()
            responseIntent.putExtra("newRequest", newRequest)
            setResult(RESULT_OK, responseIntent)
            finish()
        }

        binding.requestCancelButton.setOnClickListener {
            // torna a main
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}