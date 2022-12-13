package com.example.sharednotes.main

class Note(
    var title: String="",
    var description: String="",
    var hasReminder: Boolean=false,
    var reminder: String="",
    val recipient: String="",
    val sender: String=""
) {

}