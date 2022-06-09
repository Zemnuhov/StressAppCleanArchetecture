package com.neurotech.domain.usecases.settings

import com.neurotech.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetStimulusList(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getStimulusList()
    }
}