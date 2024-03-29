package com.example.sharednotes.main

import com.google.firebase.auth.FirebaseAuth

object AppManager {

    val myNotes = arrayListOf<Note>()
    var userEmail = ""
    lateinit var currentUser: User

    /*fun addNote(_title : String, _description : String, _reminder : String, _recipient : String, _sender : String)
    {
        val newNote = Note(title = _title, description = _description, reminder = _reminder, recipient = _recipient, sender = _sender)
        myNotes.add(newNote)
    }*/

    fun setCurrentUser(name: String, mail: String){
        userEmail = mail
        currentUser = User(name, arrayListOf(), arrayListOf())
    }

}