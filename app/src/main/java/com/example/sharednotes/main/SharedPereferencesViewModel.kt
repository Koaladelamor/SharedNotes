package com.example.sharednotes.main

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedPereferencesViewModel: ViewModel() {

    val musicState = MutableLiveData<Boolean>()
    val MUSIC_STATE_PREF = "musicState"

    fun writeMusicState(context: Context, state: Boolean){

        //Disc
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putBoolean(MUSIC_STATE_PREF, state)
        sharedPrefs.apply()

        //RAM
        musicState.postValue(state)
    }

    fun readMusicState(context: Context){
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val ms = sharedPrefs.getBoolean(MUSIC_STATE_PREF, false)
        musicState.postValue(ms)
    }
}