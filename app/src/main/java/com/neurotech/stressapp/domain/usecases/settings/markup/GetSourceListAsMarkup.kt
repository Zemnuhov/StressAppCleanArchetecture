package com.neurotech.stressapp.domain.usecases.settings.markup

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MarkupSettingsRepository

class GetSourceListAsMarkup(private val repository: MarkupSettingsRepository) {
    operator fun invoke(): LiveData<List<String>>{
        return repository.getSources()
    }
}