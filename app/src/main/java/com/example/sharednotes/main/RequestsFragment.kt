package com.example.sharednotes.main

import android.app.Activity
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
import com.example.sharednotes.databinding.FragmentRequestsBinding

class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsBinding
    private lateinit var adapter: RequestsRecyclerViewAdapter

    //private var myUser = User(AppManager.userEmail, arrayListOf(), arrayListOf())

    private val notesViewModel: NotesViewModel by viewModels()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val note = it.data?.extras?.getSerializable("newRequest")
        if (it.resultCode == Activity.RESULT_OK && note is Note)
        {
            //Toast.makeText(requireContext(), note.title, Toast.LENGTH_SHORT).show()

            notesViewModel.addRequestToUser(AppManager.currentUser, note)
            Toast.makeText(requireContext(), "Request sent", Toast.LENGTH_SHORT).show()
            //println(note.recipient)
        } else {
            //Toast.makeText(requireContext(), "Request canceled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentRequestsBinding.inflate(inflater)
        adapter = RequestsRecyclerViewAdapter(AppManager.myNotes, requireContext(), notesViewModel)
        binding.RequestsRecyclerView.adapter = adapter

        binding.createRequestButton.setOnClickListener{
            val intent = Intent(requireContext(), CreateRequestActivity::class.java)
            launcher.launch(intent)
        }

        //Toast.makeText(requireContext(), AppManager.userEmail, Toast.LENGTH_SHORT).show()

        notesViewModel.getRequestsFromUser(AppManager.currentUser)

        notesViewModel.currentRequests.observe(requireActivity()) {
            if(it != null){
                adapter.updateNotesList(it)
            }
            //Toast.makeText(requireContext(), "Notes have been updated", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}