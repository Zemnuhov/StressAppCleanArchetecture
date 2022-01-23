package com.neurotech.stressapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.stressapp.data.database.entity.PeakEntity
import com.neurotech.stressapp.data.database.entity.TonicEntity
import io.reactivex.Flowable


@Dao
interface TonicDao {

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-10 minute')")
    fun getTenMinuteAvg(): Flowable<Int>

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-1 hour')")
    fun getOneHourAvg(): Flowable<Int>

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-1 day')")
    fun getOneDayAvg(): Flowable<Int>

    @Insert
    fun insertTonicValue(vararg peak: TonicEntity)

    @Query("DELETE FROM tonicentity WHERE time >= datetime('now','-2 day')")
    fun deleteTonicValue()
}