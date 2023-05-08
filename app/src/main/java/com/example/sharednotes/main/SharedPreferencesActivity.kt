package com.example.sharednotes.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.sharednotes.databinding.ActivitySharedPreferencesBinding

class SharedPreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPreferencesBinding
    private val viewModel: SharedPereferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.writeMusicState(this, isChecked)
        }

        viewModel.musicState.observe(this){
            binding.checkBox.isChecked = it
        }

        viewModel.readMusicState(this)
    }
}