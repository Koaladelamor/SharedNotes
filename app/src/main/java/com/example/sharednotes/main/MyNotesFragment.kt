package com.example.sharednotes.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sharednotes.databinding.FragmentMyNotesBinding

class MyNotesFragment : Fragment() {
    private lateinit var binding: FragmentMyNotesBinding
    private lateinit var adapter: MyNotesRecyclerViewAdapter

    //private var myUser = User(AppManager.userEmail, arrayListOf(), arrayListOf())

    private val notesViewModel: NotesViewModel by viewModels()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val note = it.data?.extras?.getSerializable("newNote")
        if (it.resultCode == RESULT_OK && note is Note)
        {
            //Toast.makeText(requireContext(), note.title, Toast.LENGTH_SHORT).show()

            notesViewModel.addNoteToUser(AppManager.currentUser, note)
            //Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()
        } else {
            //Toast.makeText(requireContext(), "Adding note cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentMyNotesBinding.inflate(inflater)
        adapter = MyNotesRecyclerViewAdapter(AppManager.myNotes, requireContext(), notesViewModel)
        binding.MyNotesRecyclerView.adapter = adapter

        binding.addNoteButton.setOnClickListener{
            val intent = Intent(requireContext(), CreateNoteActivity::class.java)
            launcher.launch(intent)
        }

        //Toast.makeText(requireContext(), AppManager.userEmail, Toast.LENGTH_SHORT).show()

        notesViewModel.getNotesFromUser(AppManager.currentUser)

        notesViewModel.currentUserNotes.observe(requireActivity()) {
            if(it != null){
                adapter.updateNotesList(it)
            }
            //Toast.makeText(requireContext(), "Notes have been updated", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}