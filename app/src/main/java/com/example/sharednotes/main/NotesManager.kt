package com.example.sharednotes.main

import com.example.sharednotes.main.Note

object NotesManager {
    val myNotes = arrayListOf<Note>()

    fun addNote(_title : String, _description : String, _reminder : String, _recipient : String, _sender : String)
    {
        val newNote = Note(title = _title, description = _description, reminder = _reminder, recipient = _recipient, sender = _sender)
        myNotes.add(newNote)
    }
}