package com.neurotech.domain.usecases.settings

import com.neurotech.domain.repository.SettingsRepository

class GetDeviceInMemory(private val repository: SettingsRepository) {
    operator fun invoke():String?{
        return repository.deviceInMemory()
    }
}