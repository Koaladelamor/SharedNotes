package com.example.sharednotes.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sharednotes.databinding.FragmentMyNotesBinding

class MyNotesFragment : Fragment() {
    private lateinit var binding: FragmentMyNotesBinding
    private lateinit var adapter: MyNotesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newNote("Compra pan", "Urgente", "Mamá", "Hijo")
        newNote("Entrega", "Muy importante", "Yo", "")

        binding = FragmentMyNotesBinding.inflate(inflater)
        adapter = MyNotesRecyclerViewAdapter(NotesManager.myNotes, requireContext())
        binding.MyNotesRecyclerView.adapter = adapter
        adapter.updateNotesList(NotesManager.myNotes)

        return binding.root
    }

    fun newNote(_title : String, _description : String, _recipient : String, _sender : String)
    {
        val newNote = Note(title = _title, description = _description, recipient = _recipient, sender = _sender)
        NotesManager.myNotes.add(newNote)
    }
}