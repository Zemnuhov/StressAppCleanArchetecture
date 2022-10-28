package com.neurotech.domain.repository

import com.neurotech.domain.models.PeakDomainModel
import com.neurotech.domain.models.PhaseFlowDomainModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PhaseRepository {
    suspend fun getTenMinuteCountFlow(): Flow<Int>
    suspend fun getTenMinuteCount(): Int
    suspend fun getOneHourCountFlow(): Flow<Int>
    suspend fun getOneDayCountFlow(): Flow<Int>
    suspend fun getPeaksInInterval(dateTime: Date): Flow<Int>
    suspend fun writePhase(model: PeakDomainModel)
}