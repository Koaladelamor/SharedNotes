package com.example.sharednotes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharednotes.databinding.ActivityCreateNoteBinding
import com.example.sharednotes.databinding.ActivityParticlesBinding

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var myNotesFragment: MyNotesFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newNoteAddButton.setOnClickListener {
            val newNote = Note(-1,
                binding.newNoteTitle.text.toString(),
                binding.newNoteDescription.text.toString(),
                false,
                binding.newNoteReminderDate.text.toString(),
                NotesManager.userEmail,
                NotesManager.userEmail
            )

            // go to main activity
            val responseIntent = Intent()
            responseIntent.putExtra("newNote", newNote)
            setResult(RESULT_OK, responseIntent)
            finish()
        }

        binding.newNoteCancelButton.setOnClickListener {
            // torna a main
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}