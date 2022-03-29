package com.neurotech.stressapp

import android.app.Application
import com.neurotech.stressapp.di.DaggerComponent
import com.neurotech.stressapp.di.DaggerDaggerComponent

class App: Application() {
    lateinit var component: DaggerComponent

    override fun onCreate() {
        super.onCreate()
        Singleton.context = applicationContext
        component = DaggerDaggerComponent.create()
        Singleton.daggerComponent = component
    }
}