package com.neurotech.domain.usecases.markupdata

import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.repository.MarkupRepository

class GetMarkupList(private val repository: MarkupRepository) {
    suspend operator fun invoke(): List<MarkupDomainModel> {
        return repository.getMarkupList()
    }
}