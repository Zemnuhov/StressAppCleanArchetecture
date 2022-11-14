package com.neurotech.stressapp

import android.app.Application
import androidx.fragment.app.Fragment
import com.neurotech.data.di.RepositoryDI
import com.neurotech.stressapp.di.AppComponent
import com.neurotech.stressapp.di.AppModule
import com.neurotech.stressapp.di.DaggerAppComponent


class App: Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()
        RepositoryDI(applicationContext)
        /////????/////
        Singleton.context = applicationContext
    }

    fun Fragment.getAppComponent():AppComponent{
        return component
    }
}