package com.neurotech.data.modules.settings

interface Settings {
    fun saveDevice(MAC: String)
    fun getDevice(): String
    fun getThreshold(): Double
    fun setThreshold(value: Double)
    fun setDefaultMAC()
    fun getStimulusList(): List<String>
    fun addStimulus(source:String): Boolean
    fun deleteStimulus(source: String)
}