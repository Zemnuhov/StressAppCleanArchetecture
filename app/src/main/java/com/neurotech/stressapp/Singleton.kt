package com.neurotech.stressapp

import android.annotation.SuppressLint
import android.content.Context
import com.neurotech.stressapp.di.DaggerComponent

@SuppressLint("StaticFieldLeak")
object Singleton {
    const val DEBUG = false
    lateinit var context: Context
    lateinit var daggerComponent: DaggerComponent
    const val TEN_MINUTE = "10М"
    const val HOUR = "1Ч"
    const val DAY = "1Д"


}