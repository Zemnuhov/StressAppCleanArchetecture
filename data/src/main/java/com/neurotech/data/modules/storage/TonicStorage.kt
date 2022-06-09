package com.neurotech.data.modules.storage

import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.flow.Flow

interface TonicStorage {
    suspend fun getTenMinuteAvgFlow(): Flow<Int?>
    suspend fun getTenMinuteAvg(): Int
    suspend fun getOneHourAvgFlow(): Flow<Int?>
    suspend fun getOneDayAvgFlow(): Flow<Int?>
    suspend fun insertTonicValue(value: TonicEntity)
    suspend fun deleteTonicValue()

}