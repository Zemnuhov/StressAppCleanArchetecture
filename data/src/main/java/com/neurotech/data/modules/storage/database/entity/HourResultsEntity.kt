package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HourResultsEntity(
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
)
