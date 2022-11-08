package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class GetResultsInMonth(private val repository: ResultDataRepository) {
    suspend operator fun invoke(month: Date): Flow<List<ResultForTheDayDomainModel>> {
        return repository.getResultsInMonth(month)
    }
}