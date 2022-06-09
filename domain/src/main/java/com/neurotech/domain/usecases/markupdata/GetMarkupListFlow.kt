package com.neurotech.domain.usecases.markupdata

import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.repository.MarkupRepository
import kotlinx.coroutines.flow.Flow

class GetMarkupListFlow(private val repository: MarkupRepository) {
    suspend operator fun invoke(): Flow<List<MarkupDomainModel>> {
        return repository.getMarkupListFlow()
    }
}