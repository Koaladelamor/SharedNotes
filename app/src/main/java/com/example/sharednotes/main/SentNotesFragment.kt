package com.example.sharednotes.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sharednotes.databinding.FragmentSentNotesBinding

class SentNotesFragment : Fragment() {
    private lateinit var binding: FragmentSentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSentNotesBinding.inflate(inflater)
        return binding.root
    }
}