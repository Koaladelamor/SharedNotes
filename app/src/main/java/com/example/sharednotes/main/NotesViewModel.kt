package com.example.sharednotes.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesViewModel : ViewModel() {

    private var database: DatabaseReference =
        Firebase.database("https://sharednotes-d94ab-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("notes")

    var currentUserNotes = MutableLiveData<ArrayList<Note>>()

    fun getNotesFromUser(user: User){
        database.child(user.username).get().addOnSuccessListener {
            //Log.i("firebase", "Got value ${it.value}")
            val userData = it.getValue(User::class.java) ?: return@addOnSuccessListener
            currentUserNotes.postValue(userData.userNotes)
        }.addOnFailureListener{
            //Log.e("firebase", "Error getting data", it)
        }
    }

    fun addNoteToUser(user: User, note: Note){
        currentUserNotes.value?.add(note)
        notifyObservers()
        database.child(user.username).setValue(currentUserNotes.value)
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

    fun subscribeAt(user: User) {
        database.child(user.username)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val u = snapshot.getValue(User::class.java) ?: return
                    currentUserNotes.postValue(u.userNotes)
                }

                override fun onCancelled(error: DatabaseError) = Unit

            })
    }
}