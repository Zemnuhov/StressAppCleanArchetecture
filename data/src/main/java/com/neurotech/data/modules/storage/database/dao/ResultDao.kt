package com.neurotech.test.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.test.storage.database.entity.ResultEntity
import com.neurotech.test.storage.database.entity.ResultSourceCounterItem
import com.neurotech.test.storage.database.entity.ResultTimeAndPeak
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Query("SELECT COUNT(*) FROM resultentity WHERE time >= datetime('now','-9 minute','localtime')")
    fun getTenMinuteCount(): Int

    @Query("SELECT time,peakCount FROM resultentity WHERE time >= datetime('now','-1 hour','localtime')")
    fun getResultsInOneHour(): Flow<List<ResultTimeAndPeak>>

    @Query("SELECT stressCause, COUNT(*) as count  FROM resultentity WHERE stressCause in (:sources) GROUP BY stressCause")
    fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>>

    @Insert
    fun insertResult(vararg resultEntity: ResultEntity)
}