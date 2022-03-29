package com.neurotech.test.storage


import com.neurotech.test.storage.database.entity.ResultEntity
import com.neurotech.test.storage.database.entity.ResultSourceCounterItem
import com.neurotech.test.storage.database.entity.ResultTimeAndPeak
import kotlinx.coroutines.flow.Flow

interface ResultStorage {
    suspend fun getTenMinuteCount(): Int
    suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeak>>
    suspend fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>>
    suspend fun saveResult(resultEntity: ResultEntity)

}