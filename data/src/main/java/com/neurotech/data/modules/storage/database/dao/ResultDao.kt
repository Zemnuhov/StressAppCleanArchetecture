package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.ResultSourceCounterItem
import com.neurotech.data.modules.storage.database.entity.ResultTimeAndPeak
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Query("SELECT * FROM resultentity GROUP BY time")
    fun getResult(): Flow<List<ResultEntity>>

    @Query("SELECT COUNT(*) FROM resultentity WHERE time >= datetime('now','-9 minute','localtime')")
    fun getTenMinuteCount(): Int

    @Query("SELECT time,peakCount FROM resultentity WHERE time >= datetime('now','-1 hour','localtime')")
    fun getResultsInOneHour(): Flow<List<ResultTimeAndPeak>>

    @Query("SELECT stressCause, COUNT(*) as count  FROM resultentity WHERE stressCause in (:sources) GROUP BY stressCause")
    fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>>

    @Query("UPDATE resultentity SET stressCause = :stressCause WHERE time = :time")
    fun setStressCauseByTime(stressCause: String, time: List<String>)

    @Query("SELECT * FROM resultentity WHERE peakCount > :peakLimit GROUP BY time")
    fun getStressResult(peakLimit: Int): Flow<List<ResultEntity>>

    @Insert
    fun insertResult(vararg resultEntity: ResultEntity)
}