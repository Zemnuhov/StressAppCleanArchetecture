package com.neurotech.domain.repository

import com.neurotech.domain.models.*
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ResultDataRepository {
    suspend fun getResults(): Flow<List<ResultDomainModel>>
    suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeakDomainModel>>
    suspend fun getResultsTenMinuteCount(): Int
    suspend fun getCountBySources(sources: List<String>): Flow<List<ResultCountSourceDomainModel>>
    suspend fun writeResult(model: ResultDomainModel)
    suspend fun setStressCauseByTime(stressCause: String, time: List<Date>)
    suspend fun setKeepByTime(keep: String?, time: Date)
    suspend fun getGoingBeyondLimit(peakLimit: Int):Flow<List<ResultDomainModel>>
    suspend fun getResultsInMonth(month: Date): Flow<List<ResultForTheDayDomainModel>>
    suspend fun getResultsCountAndSourceInInterval(beginInterval:Date, endInterval:Date): Flow<List<ResultCountSourceDomainModel>>
    suspend fun getResultsInInterval(beginInterval:Date, endInterval:Date): Flow<List<ResultDomainModel>>
    suspend fun getUserParameterInInterval(beginInterval:Date, endInterval:Date): Flow<UserParameterDomainModel>
}