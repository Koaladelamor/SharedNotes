package com.example.sharednotes.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharednotes.databinding.ActivityCreateNoteBinding
import com.example.sharednotes.databinding.ActivityParticlesBinding

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newNoteAddButton.setOnClickListener {
            NotesManager.addNote(
                binding.newNoteTitle.text.toString(),
                binding.newNoteDescription.text.toString(),
                binding.newNoteReminderDate.text.toString(),
                "Yo", ""
            )
        }

        binding.newNoteCancelButton.setOnClickListener {
            // reset
        }
    }
}