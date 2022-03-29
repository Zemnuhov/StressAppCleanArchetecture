package com.neurotech.test.storage.database.entity

import androidx.room.ColumnInfo

data class ResultSourceCounterItem(
    @ColumnInfo(name = "stressCause")
    val source: String,
    @ColumnInfo(name = "count")
    val count: Int
)