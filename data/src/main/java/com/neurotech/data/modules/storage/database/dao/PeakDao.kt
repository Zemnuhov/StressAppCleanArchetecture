package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.test.storage.database.entity.PeakEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PeakDao {
    @Query("SELECT COUNT(*) FROM peakentity WHERE timeBegin >= datetime('now','-10 minute','localtime')")
    fun getTenMinuteCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM peakentity WHERE timeBegin >= datetime('now','-10 minute','localtime')")
    fun getTenMinuteCount(): Int

    @Query("SELECT COUNT(*) FROM peakentity WHERE timeBegin >= datetime('now','-1 hour','localtime')")
    fun getOneHourCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM peakentity WHERE timeBegin >= datetime('now','-1 day','localtime')")
    fun getOneDayCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM peakentity WHERE datetime(timeBegin, 'localtime') >= datetime(:dateTime,'localtime')")
    fun getPeaksInInterval(dateTime: String): Flow<Int>

    @Insert
    fun insertPeak(vararg peak: PeakEntity)

    @Query("DELETE FROM peakentity WHERE timeBegin >= datetime('now','-2 day')")
    fun deletePeak()
}