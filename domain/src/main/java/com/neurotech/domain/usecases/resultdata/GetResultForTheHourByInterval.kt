package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.repository.HourResultRepository
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class GetResultForTheHourByInterval(private val repository: HourResultRepository) {
    suspend operator fun invoke(beginInterval: Date, endInterval:Date): Flow<List<ResultForTheDayDomainModel>> {
        return repository.getResultForTheHour(beginInterval, endInterval)
    }
}