package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.neurotech.data.modules.storage.database.entity.HourResultsEntity
import com.neurotech.domain.models.HourResultDomain
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HourResultDao {

    @Query("SELECT date from HourResultsEntity")
    fun getDates(): List<String>

    @Query("SELECT * from HourResultsEntity")
    fun getHourResultsFlow(): Flow<List<HourResultsEntity>>

    @Query("SELECT * from HourResultsEntity WHERE date>= :beginInterval and date<= :endInterval")
    fun getResultForTheHourByInterval(beginInterval: String, endInterval: String): Flow<List<HourResultsEntity>>

    @Insert
    fun insertHourResult(vararg hourResult: HourResultsEntity)

    @Insert
    fun insertHourResults(hourResult: List<HourResultsEntity>)

    @Update
    fun updateHourResult(vararg hourResult: HourResultsEntity)
}