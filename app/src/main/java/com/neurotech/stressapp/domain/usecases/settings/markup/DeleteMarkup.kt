package com.neurotech.stressapp.domain.usecases.settings.markup

import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupCaseRepository

class DeleteMarkup(val repository: MarkupCaseRepository) {
    operator fun invoke(markup: MarkupEntity): String{
        return repository.deleteMarkup(markup)
    }
}