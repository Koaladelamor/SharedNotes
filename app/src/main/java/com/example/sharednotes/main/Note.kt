package com.example.sharednotes.main

class Note(
    var id: Int = -1,
    var title: String="",
    var description: String="",
    //var hasReminder: Boolean=false,
    //var reminder: String="",
    var recipient: String="",
    var sender: String="",
    var status: String=""
): java.io.Serializable {
    fun editNote(_title : String, _description : String, _recipient : String, _sender : String)
    {
        title = _title
        description = _description
        recipient = _recipient
        sender = _sender
    }
}