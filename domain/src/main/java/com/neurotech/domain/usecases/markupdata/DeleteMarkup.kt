package com.neurotech.domain.usecases.markupdata

import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.repository.MarkupRepository

class DeleteMarkup(private val repository: MarkupRepository) {
    suspend operator fun invoke(markup: MarkupDomainModel){
        repository.deleteMarkup(markup)
    }
}