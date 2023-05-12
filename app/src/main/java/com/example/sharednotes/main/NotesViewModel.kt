package com.example.sharednotes.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class NotesViewModel : ViewModel() {

    private var database: DatabaseReference =
        Firebase.database("https://sharednotes-d94ab-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("notes")

    var currentUserNotes = MutableLiveData<ArrayList<Note>>()
    var currentRequests = MutableLiveData<ArrayList<Note>>()
    var sharedUserNotes = MutableLiveData<ArrayList<Note>>()
    var sharedRequests = MutableLiveData<ArrayList<Note>>()
    var isListenerAdded = false
    var isRequestListenerAdded = false
    var isSharedListenerAdded = false

    fun getNotesFromUser(user: User){
        database.child(user.username).child("UserNotes").get().addOnSuccessListener {
            if (it.value == null)
            {
                database.child(user.username).child("UserNotes").setValue(user.userNotes).addOnSuccessListener {
                    if (!isListenerAdded) {
                        subscribeAtUserNotes(user)
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
                subscribeAtUserNotes(user)
            }
        }.addOnFailureListener{
            println("Error: Couldn't get notes from user.")
        }
    }

    fun getRequestsFromUser(user: User){
        database.child(user.username).child("Requests").get().addOnSuccessListener {
            if (it.value == null)
            {
                database.child(user.username).child("Requests").setValue(user.userRequests).addOnSuccessListener {
                    if (!isRequestListenerAdded) {
                        subscribeAtUserRequests(user)
                    }
                }.addOnFailureListener{
                    println("Error: Couldn't create new user.")
                }

                return@addOnSuccessListener
            }

            val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
            val requestsList = it.getValue(genericTypeIndicator)
            currentRequests.postValue(requestsList ?: arrayListOf())
            notifyObservers()

            if (requestsList != null && !isRequestListenerAdded) {
                subscribeAtUserRequests(user)
            }
        }.addOnFailureListener{
            println("Error: Couldn't get notes from user.")
        }
    }

    fun addNoteToUser(user: User, note: Note){
        currentUserNotes.value?.add(note)
        notifyObservers()
        database.child(user.username).child("UserNotes").setValue(currentUserNotes.value).addOnSuccessListener {
            if (!isListenerAdded)
                subscribeAtUserNotes(user)
        }
    }

    fun addRequestToUser(user: User, request: Note){

        val recipient = request.recipient.replace(".", "")

        if(recipient == AppManager.currentUser.username)
        {
            currentRequests.value?.add(request)
            notifyObservers()

            database.child(recipient).child("Requests").setValue(currentRequests.value).addOnSuccessListener {
                if (!isRequestListenerAdded)
                    subscribeAtUserRequests(user)
                println("alright add request")
            }.addOnFailureListener{
                println("error add request")
            }
        }
        else
        {
            database.child(recipient).child("Requests").get().addOnSuccessListener {

                if (it.value != null) {
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
                    val notesList = it.getValue(genericTypeIndicator)
                    //sharedRequests.postValue(notesList ?: arrayListOf())
                    sharedRequests.value = notesList ?: arrayListOf()
                    sharedRequests.value?.add(request)
                    //notifySharedObservers()
                }
                else{
                    var requestsList = arrayListOf<Note>()
                    requestsList.add(request)
                    sharedRequests.value = requestsList
                }

                /*if (!isSharedListenerAdded) {
                    subscribeAtSharedUserNotes(username)
                }*/
                //notifySharedObservers()

                // update user notes
                val updates: MutableMap<String, Any> = HashMap()
                sharedRequests.value?.forEachIndexed { index, note ->
                    updates[index.toString()] = note
                }
                database.child(recipient).child("Requests").updateChildren(updates)
                    .addOnSuccessListener {

                    }.addOnFailureListener{
                        println("update request failed")
                    }

            }.addOnFailureListener{
                println("Error: Couldn't get notes from user.")
            }
        }
    }

    fun acceptRequest(requestIndex: Int, recipient: String)
    {
        // request to note
        val requestToNote = currentRequests.value?.get(requestIndex)
        if(requestToNote != null)
        {
            // get de la lista de notas del usuario en firebase y la seteo en sharedUserNotes
            database.child(recipient).child("UserNotes").get().addOnSuccessListener {
                //if (it.value == null) { return@addOnSuccessListener }

                if (it.value != null) {
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
                    val notesList = it.getValue(genericTypeIndicator)
                    //sharedUserNotes.postValue(notesList ?: arrayListOf())
                    sharedUserNotes.value = notesList ?: arrayListOf()
                    sharedUserNotes.value?.add(requestToNote)
                    //notifySharedObservers()
                }
                else{
                    var notesList = arrayListOf<Note>()
                    notesList.add(requestToNote)
                    sharedUserNotes.value = notesList
                }

                /*if (!isSharedListenerAdded) {
                    subscribeAtSharedUserNotes(username)
                }*/

                notifySharedObservers()

                // update user notes
                val updates: MutableMap<String, Any> = HashMap()
                sharedUserNotes.value?.forEachIndexed { index, note ->
                    updates[index.toString()] = note
                }
                database.child(recipient).child("UserNotes").updateChildren(updates)
                    .addOnSuccessListener {

                    }.addOnFailureListener{
                        println("update request failed")
                    }

                // remove request
                currentRequests.postValue(currentRequests.value?.apply { removeAt(requestIndex) })
                notifyObservers()

                database.child(recipient).child("Requests").setValue(currentRequests.value).addOnSuccessListener {
                    println("alright update request")
                }.addOnFailureListener{
                    println("error update request")
                }


            }.addOnFailureListener{
                println("Error: Couldn't get notes from user.")
            }


        }
        else{
            println("request is NULL")
        }

    }

    fun rejectRequest(requestIndex: Int, recipient: String)
    {
        currentRequests.postValue(currentRequests.value?.apply { removeAt(requestIndex) })
        notifyObservers()

        database.child(recipient).child("Requests").setValue(currentRequests.value).addOnSuccessListener {
            println("alright reject request")
        }.addOnFailureListener{
            println("error reject request")
        }
    }

    fun deleteNote(noteIndex: Int, recipient: String)
    {
        currentUserNotes.postValue(currentUserNotes.value?.apply { removeAt(noteIndex) })
        notifyObservers()

        database.child(recipient).child("UserNotes").setValue(currentUserNotes.value).addOnSuccessListener {
            println("alright reject request")
        }.addOnFailureListener{
            println("error reject request")
        }
    }

    private fun notifyObservers() {
        // funcion para avisar a los observers de currentUserNotes porque el add note no modifica la referencia
        currentUserNotes.postValue(currentUserNotes.value)
        currentRequests.postValue(currentRequests.value)
    }
    private fun notifySharedObservers() {
        sharedUserNotes.postValue(sharedUserNotes.value)
    }

    /*fun writeUserNotes(user: User) {
        database.child(user.username).setValue(user)

        // currentNote = note
        currentUserNotes.postValue(user.userNotes)
    }*/

    private fun subscribeAtUserNotes(user: User) {
        database.child(user.username).child("UserNotes")
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

    private fun subscribeAtUserRequests(user: User) {
        database.child(user.username).child("Requests")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isRequestListenerAdded = true
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
                    val notesList = snapshot.getValue(genericTypeIndicator)
                    currentRequests.postValue(notesList ?: arrayListOf())
                }

                override fun onCancelled(error: DatabaseError) = Unit

            })
    }

    private fun subscribeAtSharedUserNotes(username: String) {
        database.child(username).child("UserNotes")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isSharedListenerAdded = true
                    val genericTypeIndicator = object : GenericTypeIndicator<ArrayList<Note>>() {}
                    val notesList = snapshot.getValue(genericTypeIndicator)
                    sharedUserNotes.postValue(notesList ?: arrayListOf())
                }

                override fun onCancelled(error: DatabaseError) = Unit

            })
    }

}