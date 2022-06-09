package com.neurotech.data.repository

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.settings.Settings
import com.neurotech.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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

    override fun getStimulusList(): Flow<List<String>> {
        stimulusFlow.value = settings.getStimulusList()
        return stimulusFlow
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