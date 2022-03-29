package com.neurotech.stressapp.domain.usecases.settings.markup

import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupCaseRepository
import com.neurotech.stressapp.domain.repository.MarkupSettingsRepository

class UpdateMarkup(val repository: MarkupSettingsRepository) {
    operator fun invoke(markupName: MarkupEntity): String{
        return repository.updateMarkup(markupName)
    }
}