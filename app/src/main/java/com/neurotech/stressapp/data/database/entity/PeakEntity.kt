package com.neurotech.stressapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeakEntity(
    @PrimaryKey
    @ColumnInfo(name = "timeBegin")
    val timeBegin: String,
    @ColumnInfo(name = "timeEnd")
    val timeEnd: String,
    @ColumnInfo(name = "max")
    val max: Double
)