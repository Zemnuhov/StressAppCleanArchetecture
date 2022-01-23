package com.neurotech.stressapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.stressapp.data.database.entity.PeakEntity
import io.reactivex.Flowable

@Dao
interface PeakDao {
    @Query("SELECT COUNT(*) FROM peakentity WHERE time >= datetime('now','-10 minute')")
    fun getTenMinuteCount(): Flowable<Int>

    @Query("SELECT COUNT(*) FROM peakentity WHERE time >= datetime('now','-1 hour')")
    fun getOneHourCount(): Flowable<Int>

    @Query("SELECT COUNT(*) FROM peakentity WHERE time >= datetime('now','-1 day')")
    fun getOneDayCount(): Flowable<Int>

    @Insert
    fun insertPeak(vararg peak: PeakEntity)

    @Query("DELETE FROM peakentity WHERE time >= datetime('now','-2 day')")
    fun deletePeak()
}