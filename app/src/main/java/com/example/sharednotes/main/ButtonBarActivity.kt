package com.example.sharednotes.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharednotes.R
import androidx.fragment.app.Fragment
import com.example.sharednotes.databinding.ActivityButtonBarBinding

class ButtonBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityButtonBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityButtonBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myNotes = MyNotesFragment()
        val requests = RequestsFragment()
        //val sentNotes = SentNotesFragment()

        binding.bottomNavigationViewClase.setOnItemSelectedListener {
            when (it.itemId){
                R.id.myNotesButton -> setFragment(myNotes)
                R.id.requestsButton -> setFragment(requests)
                //R.id.thirdButton -> setFragment(sentNotes)
            }
            true
        }
    }

    fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.fragments.forEach{
                hide(it)
            }
            if(!fragment.isAdded){
                add(binding.fragmentContainerClase.id, fragment)
            }
            else {
                show(fragment)
            }
            commit()
        }
    }
}