package com.neurotech.stressapp.data.spsettings

import android.util.Log

class SettingsApi {
    var settingsModel = Settings()
    val defaultMAC = "00:00:00:00:00:00"

    fun saveDevice(MAC: String) {
        settingsModel.setSettings(settingsModel.DEVICE_ADDRESS_TAG, MAC)
    }

    fun getDevice(): String {
        return settingsModel.getSettings(settingsModel.DEVICE_ADDRESS_TAG)
            ?: return defaultMAC
    }

    fun getThreshold(): Double {
        val threshold = settingsModel.getSettings(settingsModel.THRESHOLD_TAG)

        return when(threshold){
            is String -> threshold.toDouble()
            else -> 1.25
        }
    }

    fun setThreshold(value: Double) {
        settingsModel.setSettings(settingsModel.THRESHOLD_TAG, value.toString())
    }

    fun setDefaultMAC(){
        saveDevice(defaultMAC)
    }

    fun getStimulusList(): List<String>{
        val sources = (settingsModel.getSettings(settingsModel.STIMULUS_TAG)?:
        settingsModel.DEFAULT_STIMULUS)
            .split("|").filter { it != "" }
        return sources
    }

    fun addStimulus(source:String){
        val stimulusList = getStimulusList().toMutableList()
        stimulusList.add(source)
        settingsModel.setSettings(settingsModel.STIMULUS_TAG,stimulusList.joinToString("|"))
    }

    fun deleteStimulus(source: String){
        val stimulusList = getStimulusList().toMutableList()
        stimulusList.remove(source)
        settingsModel.setSettings(settingsModel.STIMULUS_TAG,stimulusList.joinToString("|"))
    }
}