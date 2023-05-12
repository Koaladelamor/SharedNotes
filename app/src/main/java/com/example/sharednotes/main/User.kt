package com.example.sharednotes.main

class User(
    var username: String="",
    var userNotes: ArrayList<Note> = arrayListOf(),
    var userRequests: ArrayList<Note> = arrayListOf()
) {
    /*fun editUser(_username : String, _notes : ArrayList<Note>)
    {
        username = _username
        userNotes = _notes
    }*/
}