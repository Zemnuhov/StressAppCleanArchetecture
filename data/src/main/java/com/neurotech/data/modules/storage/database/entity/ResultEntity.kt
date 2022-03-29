package com.neurotech.test.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



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
)
