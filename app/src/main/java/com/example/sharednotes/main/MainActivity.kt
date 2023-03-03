package com.example.sharednotes.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sharednotes.R
import com.example.sharednotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        intent.extras?.getBoolean("isValidUser")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myNotesFrag = MyNotesFragment()
        val requestsFrag = RequestsFragment()
        val sentFrag = SentNotesFragment()

        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.firstButton -> setFragment(myNotesFrag)
                R.id.secondButton -> setFragment(requestsFrag)
                R.id.thirdButton -> setFragment(sentFrag)
            }
            true
        }

        setFragment(myNotesFrag)
    }

    fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.fragments.forEach{
                hide(it)
            }
            if(!fragment.isAdded){
                add(binding.mainFragmentContainer.id, fragment)
            }
            else {
                show(fragment)
            }
            commit()
        }
    }
}