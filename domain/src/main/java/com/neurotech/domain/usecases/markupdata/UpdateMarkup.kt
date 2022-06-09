package com.neurotech.domain.usecases.markupdata

import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.repository.MarkupRepository

class UpdateMarkup(private val repository: MarkupRepository) {
    suspend operator fun invoke(markup: MarkupDomainModel):String {
        return repository.updateMarkup(markup)
    }
}