package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow

class GetGoingBeyondLimit(private val repository: ResultDataRepository) {
    suspend operator fun invoke(peakLimit: Int): Flow<List<ResultDomainModel>> {
        return repository.getGoingBeyondLimit(peakLimit)
    }
}