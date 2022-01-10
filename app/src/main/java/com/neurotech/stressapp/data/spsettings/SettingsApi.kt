package com.neurotech.stressapp.data.spsettings

class SettingsApi {
    var settingsModel = Settings()
    val defaultMAC = "00:00:00:00:00:00"

    fun saveDevice(MAC: String) {
        settingsModel.setSettings(settingsModel.DEVICE_ADDRESS_TAG, MAC)
    }

    fun getDevice(): String? {
        return settingsModel.getSettings(settingsModel.DEVICE_ADDRESS_TAG)
            ?: return defaultMAC
    }

    fun getThreshold(): Double {
        return settingsModel.getSettings(settingsModel.THRESHOLD_TAG)!!.toDouble()
    }

    fun setThreshold(value: Double) {
        settingsModel.setSettings(settingsModel.THRESHOLD_TAG, value.toString())
    }

    fun setDefaultMAC(){
        saveDevice(defaultMAC)
    }
}