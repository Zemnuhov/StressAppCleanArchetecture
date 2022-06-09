package com.neurotech.domain.usecases.settings

import com.neurotech.domain.repository.SettingsRepository

class GetThreshold(private val repository: SettingsRepository) {
    operator fun invoke(): Double {
        return repository.getThreshold()
    }
}