package com.neurotech.domain.repository

import com.neurotech.domain.models.DayResultDomainModel
import com.neurotech.domain.models.MaxParamDomainModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DayResultRepository {
    suspend fun getDayResultsFlow(): Flow<List<DayResultDomainModel>>
    suspend fun insertDayResult(dayResult: DayResultDomainModel)
    suspend fun getResultForTheDay(beginInterval:Date, endInterval: Date): Flow<List<DayResultDomainModel>>
    suspend fun getLastFiveValidDay(): Flow<List<DayResultDomainModel>>
    suspend fun getMaxParamAsync(): Deferred<MaxParamDomainModel>
}