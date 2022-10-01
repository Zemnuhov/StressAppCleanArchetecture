package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class GetResultsByInterval(private val repository: ResultDataRepository) {
    suspend operator fun invoke(beginInterval: Date, endInterval: Date): Flow<List<ResultDomainModel>> {
        return repository.getResultsInInterval(beginInterval, endInterval)
    }
}