package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo

data class UserParameterEntity(
    @ColumnInfo(name = "peaksInDay")
    val peaksInDay: Int,
    @ColumnInfo(name = "peaksInTenMinute")
    val peaksInTenMinute: Int,
    @ColumnInfo(name = "tonicAverage")
    val tonicAverage: Int,
)