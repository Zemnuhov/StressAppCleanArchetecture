package com.neurotech.stressapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.neurotech.stressapp.dagger.DaggerComponent
import io.reactivex.subjects.PublishSubject

@SuppressLint("StaticFieldLeak")
object Singleton {
    const val DEBUG = false
    lateinit var context: Context
    lateinit var daggerComponent: DaggerComponent
    lateinit var fragmentManager:FragmentManager
    const val TEN_MINUTE = "TEN_MINUTE"
    const val HOUR = "HOUR"
    const val DAY = "DAY"


    fun showFragment(fragment: Fragment, backStack: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(backStack)
            .commit()
    }
}