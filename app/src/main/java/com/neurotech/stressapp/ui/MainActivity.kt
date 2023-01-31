package com.neurotech.stressapp.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.cesarferreira.tempo.Tempo
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.neurotech.domain.usecases.recorddevice.RecordNotifyFlag
import com.neurotech.stressapp.App
import com.neurotech.stressapp.R
import com.neurotech.stressapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @Inject lateinit var firebaseAnalytics: FirebaseAnalytics
    @Inject lateinit var recordNotifyFlag: RecordNotifyFlag

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).component.inject(this)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navigationInit()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param("EVENT", "Application is open")
            param("LOCALTIME", Tempo.now.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    private fun navigationInit(){
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch{
            recordNotifyFlag.invoke(true)
        }

    }

    override fun onPause() {
        super.onPause()
    }
}