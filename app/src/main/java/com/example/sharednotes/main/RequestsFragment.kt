package com.example.sharednotes.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sharednotes.databinding.FragmentRequestsBinding

class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsBinding.inflate(inflater)
        return binding.root
    }
}