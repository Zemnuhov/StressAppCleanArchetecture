package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cesarferreira.tempo.toDate
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.DayResultDomainModel

@Entity
data class DayResultEntity(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "peaks")
    val peaks: Int,
    @ColumnInfo(name = "peaksAvg")
    val peaksAvg: Int,
    @ColumnInfo(name = "tonic")
    val tonic: Int,
    @ColumnInfo(name = "stressCause")
    val stressCause: String?
){
    fun mapToDomain(): DayResultDomainModel {
        return DayResultDomainModel(
            date.toDate(TimeFormat.dateFormatDataBase),
            peaks,
            peaksAvg,
            tonic,
            stressCause?: ""
        )
    }
}