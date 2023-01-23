package com.example.sharednotes.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.sharednotes.databinding.ActivityRealTimeDataBaseBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealTimeDataBaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealTimeDataBaseBinding

    private val aUserNotes: ArrayList<Note> = arrayListOf()

    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRealTimeDataBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newNote = Note(1, "compra pan", "acuerdate cabron")
        val newNote2 = Note(2, "compra agua", "fresquita plz")
        aUserNotes.add(newNote)
        aUserNotes.add(newNote2)

        val aUser = User("francisco", aUserNotes)

        notesViewModel.writeUserNotes(aUser)
        notesViewModel.subscribeAt(aUser)

        notesViewModel.currentUserNotes.observe(this) {
            Toast.makeText(this, "Something changed", Toast.LENGTH_SHORT).show()
        }
    }
}