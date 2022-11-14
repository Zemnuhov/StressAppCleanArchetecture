package com.neurotech.data.modules.settings

import com.neurotech.data.di.RepositoryDI.Companion.component
import javax.inject.Inject

class SettingsApi : Settings {

    @Inject
    lateinit var settingsModel: SharedPrefSettings
    private val defaultMAC = "00:00:00:00:00:00"

    init {
        component.inject(this)
    }

    override fun saveDevice(MAC: String) {
        settingsModel.setSettings(settingsModel.DEVICE_ADDRESS_TAG, MAC)
    }

    override fun getDevice(): String {
        return settingsModel.getSettings(settingsModel.DEVICE_ADDRESS_TAG)
            ?: return defaultMAC
    }

    override fun getThreshold(): Double {
        return when (val threshold = settingsModel.getSettings(settingsModel.THRESHOLD_TAG)) {
            is String -> threshold.toDouble()
            else -> 3.0
        }
    }

    override fun setThreshold(value: Double) {
        settingsModel.setSettings(settingsModel.THRESHOLD_TAG, value.toString())
    }

    override fun setDefaultMAC() {
        saveDevice(defaultMAC)
    }

    override fun getStimulusList(): List<String> {
        return (settingsModel.getSettings(settingsModel.STIMULUS_TAG)
            ?: settingsModel.DEFAULT_STIMULUS)
            .split("|").filter { it != "" }
    }

    override fun addStimulus(source: String): Boolean {
        if (getStimulusList().size < 15) {
            val stimulusList = getStimulusList().toMutableList()
            stimulusList.add(source.trim())
            settingsModel.setSettings(settingsModel.STIMULUS_TAG, stimulusList.joinToString("|"))
            return true
        }
        return false
    }

    override fun deleteStimulus(source: String) {
        val stimulusList = getStimulusList().toMutableList()
        stimulusList.remove(source)
        settingsModel.setSettings(settingsModel.STIMULUS_TAG, stimulusList.joinToString("|"))
    }

    override fun getDefaultMac(): String {
        return defaultMAC
    }
}