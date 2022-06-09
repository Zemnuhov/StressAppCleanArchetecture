package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow

class GetResultsInOneHour(private val repository: ResultDataRepository) {
    suspend operator fun invoke(): Flow<List<ResultTimeAndPeakDomainModel>> {
        return repository.getResultsInOneHour()
    }
}