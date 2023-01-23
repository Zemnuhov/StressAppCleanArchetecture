package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.neurotech.data.modules.storage.database.entity.DayResultEntity
import com.neurotech.data.modules.storage.database.models.MaxParamInDayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayResultDao {
    @Query("SELECT date from DayResultEntity")
    fun getDates(): List<String>

    @Query("SELECT * from DayResultEntity")
    fun getDayResultsFlow(): Flow<List<DayResultEntity>>

    @Query("SELECT * from DayResultEntity WHERE date>= :beginInterval and date<= :endInterval")
    fun getResultForTheDayByInterval(beginInterval: String, endInterval: String): Flow<List<DayResultEntity>>

    @Query("SELECT * FROM DayResultEntity ORDER BY date DESC LIMIT 5")
    fun getLastFiveValidDay(): Flow<List<DayResultEntity>>

    @Query("SELECT MAX(tonic) as maxTonic, " +
            "MAX(peaks) as maxPeakInDay, " +
            "MAX(peaksAvg) as maxPeaksInTenMinute " +
            "FROM DayResultEntity " +
            "WHERE peaks > peaksAvg * 144 - 300 AND peaks < peaksAvg * 144 + 300")
    fun getMaxParameters(): MaxParamInDayEntity

    @Insert
    fun insertDayResult(vararg dayResult: DayResultEntity)

    @Update
    fun updateDayResult(vararg dayResult: DayResultEntity)

}