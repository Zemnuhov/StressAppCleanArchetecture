package com.neurotech.domain.usecases.settings

import com.neurotech.domain.repository.SettingsRepository

class DeleteStimulus(private val repository: SettingsRepository) {
    operator fun invoke(source:String){
        repository.deleteStimulus(source)
    }
}