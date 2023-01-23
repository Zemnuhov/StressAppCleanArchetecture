package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.data.modules.storage.database.entity.*
import com.neurotech.data.modules.storage.database.models.ResultSourceCounterItem
import com.neurotech.data.modules.storage.database.models.ResultTimeAndPeak
import com.neurotech.data.modules.storage.database.models.UserParameterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Query("SELECT * FROM resultentity GROUP BY time")
    fun getResult(): Flow<List<ResultEntity>>

    @Query("SELECT COUNT(*) FROM resultentity")
    fun getCountResult(): Flow<Int>

    @Query("SELECT * FROM resultentity WHERE time in (:times)")
    fun getResultInTimes(times: List<String>): List<ResultEntity>

    @Query("SELECT COUNT(*) FROM resultentity WHERE time >= datetime('now','-9 minute','localtime')")
    fun getTenMinuteCount(): Int

    @Query("SELECT time,peakCount FROM resultentity WHERE time >= datetime('now','-1 hour','localtime')")
    fun getResultsInOneHour(): Flow<List<ResultTimeAndPeak>>

    @Query("Select * from ResultEntity where datetime(time, 'localtime') between datetime(:beginInterval, 'localtime') and datetime(:endInterval, 'localtime')")
    fun getResultsInInterval(beginInterval: String, endInterval:String): Flow<List<ResultEntity>>

    @Query("SELECT stressCause, COUNT(*) as count  FROM resultentity WHERE stressCause in (:sources) GROUP BY stressCause")
    fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>>

    @Query("UPDATE resultentity SET stressCause = :stressCause WHERE time in (:time)")
    suspend fun setStressCauseByTime(stressCause: String, time: List<String>)

    @Query("UPDATE resultentity SET keep = :keep WHERE time = :time")
    fun setKeepByTime(keep: String?, time: String)

    @Query("SELECT * FROM resultentity WHERE peakCount > :peakLimit GROUP BY time")
    fun getStressResult(peakLimit: Int): Flow<List<ResultEntity>>

    @Query("select stressCause, COUNT(*) as count from ResultEntity where stressCause in (:sources) and datetime(time,'localtime') between datetime(:beginInterval, 'localtime') and datetime(:endInterval, 'localtime') group by stressCause ")
    fun getCountStressCauseInInterval(sources: List<String>, beginInterval: String, endInterval:String): Flow<List<ResultSourceCounterItem>>

    @Query("select date(time,'localtime') as date, SUM(peakCount) as peaks, AVG(peakCount) as peaksAvg, AVG(tonicAvg) as tonic,s as stressCause " +
            "from ResultEntity " +
            "join (select  day,s,max(count) from ResultEntity join(select date(time) as day, stressCause as s, count(stressCause) as count from resultentity group by day, stressCause) group by day) on day = date(time,'localtime') " +
            "where date >= date(:beginInterval, 'localtime') and date <= date(:endInterval, 'localtime') " +
            "group by date")
    fun getResultForTheDay(beginInterval: String, endInterval:String): List<DayResultEntity>

    @Query("select strftime('%Y-%m-%d %H', time) as date, SUM(peakCount) as peaks, AVG(peakCount) as peaksAvg, AVG(tonicAvg) as tonic,s as stressCause " +
            "from ResultEntity " +
            "join (select  day,s,max(count)" +
            " from ResultEntity join(select strftime('%Y-%m-%d %H', time) as day, stressCause as s, count(stressCause) as count from resultentity group by day, stressCause) group by day) on day = strftime('%Y-%m-%d %H', time) " +
            "where date >= datetime(:beginInterval, 'localtime') and date <= datetime(:endInterval, 'localtime') group by date")
    fun getResultForTheHour(beginInterval: String, endInterval:String): List<HourResultsEntity>

    @Query("SELECT" +
            "   AVG(tonicAvg) AS Tonic," +
            "   AVG(peakCount) AS TenMinutePhase," +
            "   (SELECT AVG(peaks) FROM HourResultsEntity WHERE date >= strftime('%Y-%m-%d %H', :beginInterval)  AND date <= strftime('%Y-%m-%d %H', :endInterval) ) AS HourPhase," +
            "   (SELECT AVG(peaks) FROM DayResultEntity WHERE date >= strftime('%Y-%m-%d', :beginInterval)  AND date <= strftime('%Y-%m-%d', :endInterval)) AS DayPhase " +
            "FROM ResultEntity " +
            "WHERE time >= :beginInterval  AND time <= :endInterval")
    fun getUserParameterInInterval(beginInterval: String, endInterval:String): Flow<UserParameterEntity>

    @Query("SELECT" +
            "   AVG(tonicAvg) AS Tonic," +
            "   AVG(peakCount) AS TenMinutePhase," +
            "   (SELECT AVG(peaks) FROM HourResultsEntity) AS HourPhase," +
            "   (SELECT AVG(peaks) FROM DayResultEntity) AS DayPhase " +
            "FROM ResultEntity")
    fun getUserParameter(): UserParameterEntity

    @Insert
    fun insertResult(vararg resultEntity: ResultEntity)
}

