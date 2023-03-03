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

    private val notesViewModel: NotesViewModel by viewModels()

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val note = it.data?.extras?.getSerializable("newNote")
        if (it.resultCode == RESULT_OK && note is Note){
//            notesViewModel.addNoteToUser(null, note)
        } else {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //newNote("Compra pan", "Urgente", "Mamá", "Hijo")
        //newNote("Entrega", "Muy importante", "Yo", "")

        binding = FragmentMyNotesBinding.inflate(inflater)
        adapter = MyNotesRecyclerViewAdapter(NotesManager.myNotes, requireContext())
        binding.MyNotesRecyclerView.adapter = adapter
        //adapter.updateNotesList(NotesManager.myNotes)

        binding.addNoteButton.setOnClickListener{
            val intent = Intent(requireContext(), CreateNoteActivity::class.java)
//            startActivity(intent)
            launcher.launch(intent)
        }

        val aUserNotes: ArrayList<Note> = arrayListOf()
//        val newNote = Note(1, "compra pan", "acuerdate cabron")
//        val newNote2 = Note(2, "compra agua", "fresquita plz")
//        aUserNotes.add(newNote)
//        aUserNotes.add(newNote2)
        val aUser = User("francisco", aUserNotes)

        notesViewModel.getNotesFromUser(aUser)
        notesViewModel.subscribeAt(aUser)

        notesViewModel.currentUserNotes.observe(requireActivity()) {
            adapter.updateNotesList(it)
            Toast.makeText(requireContext(), "Something changed", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    fun newNote(_title : String, _description : String, _recipient : String, _sender : String)
    {
        val newNote = Note(title = _title, description = _description, recipient = _recipient, sender = _sender)
        NotesManager.myNotes.add(newNote)
    }
}