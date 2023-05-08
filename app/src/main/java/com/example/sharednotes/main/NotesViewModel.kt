package com.example.sharednotes.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesViewModel : ViewModel() {

    private var database: DatabaseReference =
        Firebase.database("https://sharednotes-d94ab-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("notes")

    var currentUserNotes = MutableLiveData<ArrayList<Note>>()
    var isListenerAdded = false


    fun getNotesFromUser(user: User){
        database.child(user.username).get().addOnSuccessListener {
            if (it.value == null)
            {
                database.child(user.username).setValue(user.userNotes).addOnSuccessListener {
                    if (!isListenerAdded) {
                        subscribeAt(user)
                    }
                }.addOnFailureListener{
                    println("Error: Couldn't create new user.")
                }

                return@addOnSuccessListener
            }

            val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
            val notesList = it.getValue(genericTypeIndicator)
            currentUserNotes.postValue(notesList ?: arrayListOf())
            notifyObservers()

            if (notesList != null && !isListenerAdded) {
                subscribeAt(user)
            }
        }.addOnFailureListener{
            println("Error: Couldn't get notes from user.")
        }
    }

    fun addNoteToUser(user: User, note: Note){
        currentUserNotes.value?.add(note)
        notifyObservers()
        database.child(user.username).setValue(currentUserNotes.value).addOnSuccessListener {
            if (!isListenerAdded)
                subscribeAt(user)
        }
    }

    private fun notifyObservers() {
        // funcion para avisar a los observers de currentUserNotes porque el add note no modifica la referencia
        currentUserNotes.postValue(currentUserNotes.value)
    }

    fun writeUserNotes(user: User) {
        database.child(user.username).setValue(user)

        // currentNote = note
        currentUserNotes.postValue(user.userNotes)
    }

    private fun subscribeAt(user: User) {
        database.child(user.username)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isListenerAdded = true
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
                    val notesList = snapshot.getValue(genericTypeIndicator)
                    currentUserNotes.postValue(notesList ?: arrayListOf())
                }

                override fun onCancelled(error: DatabaseError) = Unit

            })
    }

}