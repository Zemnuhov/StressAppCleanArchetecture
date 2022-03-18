package com.neurotech.stressapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.stressapp.data.database.entity.PeakEntity
import io.reactivex.Flowable
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

    @Insert
    fun insertPeak(vararg peak: PeakEntity)

    @Query("DELETE FROM peakentity WHERE timeBegin >= datetime('now','-2 day')")
    fun deletePeak()
}