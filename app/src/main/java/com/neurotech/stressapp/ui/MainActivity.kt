package com.neurotech.stressapp.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.ui.fragment.MainFragment
import com.neurotech.stressapp.ui.fragment.SearchFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var appMenu: Menu
    @Inject
    lateinit var bleConnection: BleConnection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).component.inject(this)
        Singleton.fragmentManager = supportFragmentManager


    }
    private fun startApplication(){
        if(bleConnection.connectionState == BleConnection.CONNECTED){
            Singleton.showFragment(MainFragment(),"base")
        }else{
            Singleton.showFragment(SearchFragment(),"connection")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        appMenu = menu!!
        menu.findItem(R.id.menu_search).isVisible = false
        startApplication()
        return super.onCreateOptionsMenu(menu)
    }
}