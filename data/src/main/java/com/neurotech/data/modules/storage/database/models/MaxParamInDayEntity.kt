package com.neurotech.data.modules.storage.database.models

import androidx.room.ColumnInfo
import com.neurotech.domain.models.MaxParamDomainModel

data class MaxParamInDayEntity(
    @ColumnInfo(name = "maxTonic")
    val maxTonic: Int,
    @ColumnInfo(name = "maxPeakInDay")
    val maxPeakInDay: Int,
    @ColumnInfo(name = "maxPeaksInTenMinute")
    val maxPeaksInTenMinute: Int
){
    fun mapToDomain(): MaxParamDomainModel{
        return MaxParamDomainModel(
            maxTonic,
            maxPeakInDay,
            maxPeaksInTenMinute
        )
    }
}
