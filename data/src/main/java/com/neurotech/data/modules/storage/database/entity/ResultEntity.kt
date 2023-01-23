package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cesarferreira.tempo.beginningOfMinute
import com.cesarferreira.tempo.toDate
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel


@Entity
data class ResultEntity(
    @PrimaryKey
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "peakCount")
    val peakCount: Int,
    @ColumnInfo(name = "tonicAvg")
    val tonicAvg: Int,
    @ColumnInfo(name = "conditionAssessment")
    val conditionAssessment: Int,
    @ColumnInfo(name = "stressCause")
    val stressCause: String? = null,
    @ColumnInfo(name = "keep")
    val keep: String? = null
){

    fun toDomain(): ResultDomainModel{
        return ResultDomainModel(
            time.toDate(TimeFormat.dateTimeFormatDataBase).beginningOfMinute,
            peakCount,
            tonicAvg,
            conditionAssessment,
            stressCause,
            keep
        )
    }
}
