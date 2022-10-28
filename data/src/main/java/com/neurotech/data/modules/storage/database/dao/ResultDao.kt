package com.neurotech.data.modules.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.ResultForTheDay
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

    @Query("Select * from ResultEntity where datetime(time, 'localtime') between datetime(:beginInterval, 'localtime') and datetime(:endInterval, 'localtime')")
    fun getResultsInInterval(beginInterval: String, endInterval:String): Flow<List<ResultEntity>>

    @Query("SELECT stressCause, COUNT(*) as count  FROM resultentity WHERE stressCause in (:sources) GROUP BY stressCause")
    fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>>

    @Query("UPDATE resultentity SET stressCause = :stressCause WHERE time in (:time)")
    fun setStressCauseByTime(stressCause: String, time: List<String>)

    @Query("UPDATE resultentity SET keep = :keep WHERE time = :time")
    fun setKeepByTime(keep: String?, time: String)

    @Query("SELECT * FROM resultentity WHERE peakCount > :peakLimit GROUP BY time")
    fun getStressResult(peakLimit: Int): Flow<List<ResultEntity>>

    @Query("select stressCause, COUNT(*) as count from ResultEntity where stressCause in (:sources) and datetime(time,'localtime') between datetime(:beginInterval, 'localtime') and datetime(:endInterval, 'localtime') group by stressCause ")
    fun getCountStressCauseInInterval(sources: List<String>, beginInterval: String, endInterval:String): Flow<List<ResultSourceCounterItem>>

    @Query("select  date(time,'localtime') as date," +
            " sum(peakCount) as peaks," +
            "  avg(tonicAvg) as tonic," +
            "  a.stressCause" +
            "  from ResultEntity" +
            "   join " +
            "   (select day, " +
            "stressCause, " +
            "max(count) " +
            "from (select date(time,'localtime') as day, stressCause, count(*) as count " +
            "from ResultEntity " +
            "where stressCause not null group by stressCause, date(time,'localtime'))" +
            " group by day) as a" +
            "   on date(time,'localtime') = day where datetime(time,'localtime') between datetime(:beginInterval,'localtime') and datetime(:endInterval,'localtime')" +
            "   group by date(time,'localtime')")
    fun getResultForTheDay(beginInterval: String, endInterval:String): Flow<List<ResultForTheDay>>

    @Insert
    fun insertResult(vararg resultEntity: ResultEntity)
    
}

