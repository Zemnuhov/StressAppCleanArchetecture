package com.neurotech.domain.usecases.settings

import com.neurotech.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStimulusList(private val repository: SettingsRepository) {
    fun getFlow(): Flow<List<String>> {
        return repository.getStimulusListFlow()
    }

    fun getList(): List<String> {
        return repository.getStimulusList()
    }
}