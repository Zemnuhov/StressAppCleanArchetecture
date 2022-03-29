package com.neurotech.test.storage


import com.neurotech.test.storage.database.entity.PeakEntity
import kotlinx.coroutines.flow.Flow

interface PeakStorage {
    suspend fun getTenMinuteCountFlow(): Flow<Int>
    suspend fun getTenMinuteCount(): Int
    suspend fun getOneHourCountFlow(): Flow<Int>
    suspend fun getOneDayCountFlow(): Flow<Int>
    suspend fun savePeak(peak: PeakEntity)
    suspend fun deletePeak()
}