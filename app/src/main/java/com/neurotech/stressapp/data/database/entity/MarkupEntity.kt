package com.neurotech.stressapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MarkupEntity(
    @PrimaryKey
    @ColumnInfo(name = "markupName")
    val markupName: String,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "firstSource")
    val firstSource: String?,
    @ColumnInfo(name = "secondSource")
    val secondSource: String?,
)