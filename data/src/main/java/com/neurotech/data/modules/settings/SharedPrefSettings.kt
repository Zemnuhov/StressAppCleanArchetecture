package com.neurotech.test.settings

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefSettings(private val context: Context) {
    //------------TAGS------------//
    private val SHARED_PREFERENCES_TAG = "STRESS_APP"
    val DEVICE_ADDRESS_TAG = "DEVICE_ADDRESS"
    val STIMULUS_TAG = "STIMULUS"
    val SCHEDULE_TAG = "SCHEDULE"
    val THRESHOLD_TAG = "THRESHOLD"

    //------------Default Settings------------//
    val DEFAULT_STIMULUS = "Семья|Работа|Друзья|Здоровье|Артефакты"
    val DEFAULT_THRESHOLD = "1.25"

    private var sPref: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE)


    fun setSettings(TAG: String, setting: String) {
        val ed = sPref.edit()
        ed.putString(TAG, setting)
        ed.apply()
        Log.i("StressApp_SP", "$TAG $setting written")
    }

    fun getSettings(TAG: String): String? {
        return sPref.getString(TAG, null)
    }
}