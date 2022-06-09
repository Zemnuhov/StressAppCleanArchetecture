package com.neurotech.data

import android.util.Log

object DataModuleLog {
    private const val debug = true

    fun appLog(message: String){
        if (debug){
            Log.e("StressAppDataModule",message)
        }
    }
}
