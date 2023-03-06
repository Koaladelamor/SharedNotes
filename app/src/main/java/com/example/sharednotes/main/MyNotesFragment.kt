package com.example.sharednotes.main

import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sharednotes.databinding.FragmentMyNotesBinding

class MyNotesFragment : Fragment() {
    private lateinit var binding: FragmentMyNotesBinding
    private lateinit var adapter: MyNotesRecyclerViewAdapter

    private var myUser = User(NotesManager.userEmail, arrayListOf())

    private val notesViewModel: NotesViewModel by viewModels()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val note = it.data?.extras?.getSerializable("newNote")
        if (it.resultCode == RESULT_OK && note is Note)
        {
            //Toast.makeText(requireContext(), note.title, Toast.LENGTH_SHORT).show()

            notesViewModel.addNoteToUser(myUser, note)
            Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Adding note cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentMyNotesBinding.inflate(inflater)
        adapter = MyNotesRecyclerViewAdapter(NotesManager.myNotes, requireContext())
        binding.MyNotesRecyclerView.adapter = adapter

        binding.addNoteButton.setOnClickListener{
            val intent = Intent(requireContext(), CreateNoteActivity::class.java)
            launcher.launch(intent)
        }

        Toast.makeText(requireContext(), NotesManager.userEmail, Toast.LENGTH_SHORT).show()

        notesViewModel.getNotesFromUser(myUser)

        notesViewModel.currentUserNotes.observe(requireActivity()) {
            adapter.updateNotesList(it)
            //Toast.makeText(requireContext(), "Notes have been updated", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    fun newNote(_title : String, _description : String, _recipient : String, _sender : String)
    {
        val newNote = Note(title = _title, description = _description, recipient = _recipient, sender = _sender)
        NotesManager.myNotes.add(newNote)
    }
}