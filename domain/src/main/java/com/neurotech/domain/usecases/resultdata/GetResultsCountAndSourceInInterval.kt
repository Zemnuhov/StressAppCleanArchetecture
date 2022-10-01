package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class GetResultsCountAndSourceInInterval(private val repository: ResultDataRepository) {
        suspend operator fun invoke(beginInterval: Date, endInterval:Date): Flow<List<ResultCountSourceDomainModel>> {
            return repository.getResultsCountAndSourceInInterval(beginInterval, endInterval)
        }
}