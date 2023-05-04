package com.example.sharednotes.main

import com.google.firebase.auth.FirebaseAuth

object AppManager {

    public lateinit var firebaseAuth: FirebaseAuth
    val myNotes = arrayListOf<Note>()

    var userEmail = ""

    fun addNote(_title : String, _description : String, _reminder : String, _recipient : String, _sender : String)
    {
        val newNote = Note(title = _title, description = _description, reminder = _reminder, recipient = _recipient, sender = _sender)
        myNotes.add(newNote)
    }

    fun setFireBaseInstance(firebaseInstance : FirebaseAuth){
        firebaseAuth = firebaseInstance
    }
}