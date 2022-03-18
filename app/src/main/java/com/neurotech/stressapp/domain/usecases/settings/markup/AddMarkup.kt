package com.neurotech.stressapp.domain.usecases.settings.markup

import com.neurotech.stressapp.domain.repository.MarkupCaseRepository
import com.neurotech.stressapp.domain.repository.SourceCaseRepository

class AddMarkup(val repository: MarkupCaseRepository) {
    operator fun invoke(markupName: String):String{
        return repository.addMarkup(markupName)
    }
}