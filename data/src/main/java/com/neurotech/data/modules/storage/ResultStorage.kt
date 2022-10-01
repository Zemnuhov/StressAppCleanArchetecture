package com.neurotech.data.modules.storage


import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.ResultSourceCounterItem
import com.neurotech.data.modules.storage.database.entity.ResultTimeAndPeak
import kotlinx.coroutines.flow.Flow

interface ResultStorage {
    suspend fun getTenMinuteCount(): Int
    suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeak>>
    suspend fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>>
    suspend fun setStressCauseByTime(stressCause:String, time: List<String>)
    suspend fun saveResult(resultEntity: ResultEntity)
    suspend fun getResultsInInterval(beginInterval: String, endInterval:String): Flow<List<ResultEntity>>

}