package com.neurotech.data.repository

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.settings.Settings
import com.neurotech.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SettingsRepositoryImpl: SettingsRepository {
    @Inject
    lateinit var settings: Settings
    private val stimulusFlow = MutableStateFlow<List<String>>(listOf())

    init {
        component.inject(this)
    }

    override fun getThreshold(): Double {
        return settings.getThreshold()
    }

    override fun getStimulusListFlow(): Flow<List<String>> {
        stimulusFlow.value = settings.getStimulusList()
        return stimulusFlow
    }

    override fun getStimulusList(): List<String> {
        return settings.getStimulusList()
    }

    override fun addStimulus(source: String) {
        settings.addStimulus(source)
        stimulusFlow.value = settings.getStimulusList()
    }

    override fun deleteStimulus(source: String) {
        settings.deleteStimulus(source)
        stimulusFlow.value = settings.getStimulusList()
    }
}