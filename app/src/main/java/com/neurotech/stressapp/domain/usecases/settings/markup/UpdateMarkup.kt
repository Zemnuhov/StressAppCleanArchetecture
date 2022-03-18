package com.neurotech.stressapp.domain.usecases.settings.markup

import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupCaseRepository

class UpdateMarkup(val repository: MarkupCaseRepository) {
    operator fun invoke(markupName: MarkupEntity): String{
        return repository.deleteMarkup(markupName)
    }
}