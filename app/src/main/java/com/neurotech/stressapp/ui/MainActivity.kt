package com.neurotech.stressapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Singleton.fragmentManager = supportFragmentManager
        Singleton.showFragment(SearchFragment(),"connection")

    }
}