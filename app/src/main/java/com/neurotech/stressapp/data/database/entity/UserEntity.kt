package com.neurotech.stressapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id:Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "tonicAvg")
    val tonicAvg: Int,
    @ColumnInfo(name = "peakNormal")
    val peakNormal:Int
)