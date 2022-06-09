package com.neurotech.domain.repository

import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import kotlinx.coroutines.flow.Flow

interface ResultDataRepository {
    suspend fun getResults(): Flow<List<ResultDomainModel>>
    suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeakDomainModel>>
    suspend fun getResultsTenMinuteCount(): Int
    suspend fun getCountBySources(sources: List<String>): Flow<List<ResultCountSourceDomainModel>>
    suspend fun writeResult(model: ResultDomainModel)
    suspend fun setStressCauseByTime(stressCause: String, time: List<String>)
    suspend fun getGoingBeyondLimit(peakLimit: Int):Flow<List<ResultDomainModel>>
}