package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow

class GetResultsInMonth(private val repository: ResultDataRepository) {
    suspend operator fun invoke(): Flow<List<ResultForTheDayDomainModel>> {
        return repository.getResultsInMonth()
    }
}