package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo

data class ResultForTheDay(
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "peaks")
    val peaks: Int,
    @ColumnInfo(name = "tonic")
    val tonic: Int,
    @ColumnInfo(name = "stressCause")
    val stressCause: String
)
