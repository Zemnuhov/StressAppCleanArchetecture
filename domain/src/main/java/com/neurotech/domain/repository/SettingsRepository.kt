package com.neurotech.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getThreshold(): Double
    fun getStimulusList(): Flow<List<String>>
    fun addStimulus(source:String)
    fun deleteStimulus(source: String)
}