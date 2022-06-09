package com.neurotech.domain.repository

import com.neurotech.domain.models.TonicFlowDomainModel
import kotlinx.coroutines.flow.Flow

interface TonicRepository {
    suspend fun getTenMinuteAvgFlow(): Flow<Int?>
    suspend fun getTenMinuteAvg(): Int
    suspend fun getOneHourAvgFlow(): Flow<Int?>
    suspend fun getOneDayAvgFlow(): Flow<Int?>
    suspend fun writeTonic(model: TonicFlowDomainModel)
}