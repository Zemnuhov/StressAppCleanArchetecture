package com.neurotech.data.modules.storage.database.models

import androidx.room.ColumnInfo

data class UserParameterEntity(
    @ColumnInfo(name = "Tonic")
    val tonic: Int,
    @ColumnInfo(name = "TenMinutePhase")
    val tenMinutePhase: Int,
    @ColumnInfo(name = "HourPhase")
    val hourPhase: Int,
    @ColumnInfo(name = "DayPhase")
    val dayPhase: Int


)