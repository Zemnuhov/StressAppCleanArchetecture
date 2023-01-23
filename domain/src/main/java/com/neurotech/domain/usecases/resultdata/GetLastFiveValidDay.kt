package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.DayResultDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.repository.DayResultRepository
import kotlinx.coroutines.flow.Flow

class GetLastFiveValidDay(private val repository: DayResultRepository) {
    suspend operator fun invoke(): Flow<List<DayResultDomainModel>> {
        return repository.getLastFiveValidDay()
    }
}