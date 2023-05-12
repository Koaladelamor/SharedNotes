package com.example.sharednotes.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.sharednotes.R
import com.example.sharednotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var darkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        intent.extras?.getBoolean("isValidUser")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myNotesFrag = MyNotesFragment()
        val requestsFrag = RequestsFragment()
        //val sentFrag = SentNotesFragment()

        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.myNotesButton -> setFragment(myNotesFrag)
                R.id.requestsButton -> setFragment(requestsFrag)
                //R.id.thirdButton -> setFragment(sentFrag)
            }
            true
        }

        setFragment(myNotesFrag)

        if(darkModeIsOn()){
            darkMode = true
            binding.darkModeButton.text = "Day mode"
        }

        binding.darkModeButton.setOnClickListener {
            if(darkMode)
            {
                binding.darkModeButton.text = "Day mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                darkMode = false
            }
            else
            {
                binding.darkModeButton.text = "Dark mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkMode = true
            }
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val welcome = "Signed as " + AppManager.userEmail
        Toast.makeText(this, welcome, Toast.LENGTH_SHORT).show()
    }

    private fun darkModeIsOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
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