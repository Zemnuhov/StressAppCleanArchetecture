package com.neurotech.data.modules.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity
data class UserEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "dateOfBirth")
    val dateOfBirth: String?,
    @ColumnInfo(name = "gender")
    val gender: String?,
    @ColumnInfo(name = "tonicAvg")
    val tonicAvg: Int,
    @ColumnInfo(name = "peakInDayNormal")
    val peakInDayNormal:Int,
    @ColumnInfo(name = "peakInHourNormal")
    val peakInHourNormal:Int,
    @ColumnInfo(name = "peakNormal")
    val peakNormal:Int
)