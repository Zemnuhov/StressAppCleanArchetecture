package com.neurotech.domain.repository

import com.neurotech.domain.models.HourResultDomain
import com.neurotech.domain.models.ResultForTheDayDomainModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface HourResultRepository {
    suspend fun getHourResultsFlow(): Flow<List<HourResultDomain>>
    suspend fun insertHourResult(hourResult: HourResultDomain)
    suspend fun insertHourResults(hourResults: List<HourResultDomain>)
    suspend fun getDates():List<Date>
    suspend fun updateHourResult(hourResult: HourResultDomain)
    suspend fun getResultForTheHour(beginInterval: Date, endInterval: Date): Flow<List<ResultForTheDayDomainModel>>
}