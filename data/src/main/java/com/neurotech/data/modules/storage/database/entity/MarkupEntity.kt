package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MarkupEntity(
    @PrimaryKey
    @ColumnInfo(name = "markupName")
    val markupName: String,
    @ColumnInfo(name = "timeBegin")
    var timeBegin: String?,
    @ColumnInfo(name = "timeEnd")
    var timeEnd: String?,
    @ColumnInfo(name = "firstSource")
    var firstSource: String?,
    @ColumnInfo(name = "secondSource")
    var secondSource: String?
) : Serializable