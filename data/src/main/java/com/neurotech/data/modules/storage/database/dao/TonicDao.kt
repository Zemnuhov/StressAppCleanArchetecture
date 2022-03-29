package com.neurotech.test.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TonicDao {

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-10 minute','localtime')")
    fun getTenMinuteAvgFlow(): Flow<Int?>

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-10 minute','localtime')")
    fun getTenMinuteAvg(): Int

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-1 hour','localtime')")
    fun getOneHourAvgFlow(): Flow<Int?>

    @Query("SELECT AVG(value) FROM tonicentity WHERE time >= datetime('now','-1 day','localtime')")
    fun getOneDayAvgFlow(): Flow<Int?>

    @Insert
    fun insertTonicValue(vararg peak: TonicEntity)

    @Query("DELETE FROM tonicentity WHERE time >= datetime('now','-2 day','localtime')")
    fun deleteTonicValue()
}