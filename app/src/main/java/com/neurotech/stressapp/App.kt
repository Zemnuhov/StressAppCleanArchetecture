package com.neurotech.stressapp

import android.app.Application
import com.neurotech.stressapp.dagger.DaggerComponent
import com.neurotech.stressapp.dagger.DaggerDaggerComponent
import com.neurotech.stressapp.data.BleConnection

class App: Application() {
    lateinit var component: DaggerComponent

    override fun onCreate() {
        super.onCreate()
        Singleton.context = applicationContext
        component = DaggerDaggerComponent.create()
        Singleton.daggerComponent = component
    }
}