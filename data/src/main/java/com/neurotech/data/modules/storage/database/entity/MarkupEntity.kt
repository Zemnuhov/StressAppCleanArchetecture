package com.neurotech.test.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MarkupEntity(
    @PrimaryKey
    @ColumnInfo(name = "markupName")
    val markupName: String,
    @ColumnInfo(name = "time")
    var time: String?,
    @ColumnInfo(name = "firstSource")
    var firstSource: String?,
    @ColumnInfo(name = "secondSource")
    var secondSource: String?,
) : Serializable