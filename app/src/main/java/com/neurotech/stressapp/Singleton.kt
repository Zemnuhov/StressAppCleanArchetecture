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
    const val TEN_MINUTE = "10М"
    const val HOUR = "1Ч"
    const val DAY = "1Д"


}