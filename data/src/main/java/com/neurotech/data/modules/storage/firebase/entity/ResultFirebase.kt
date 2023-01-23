package com.neurotech.data.modules.storage.firebase.entity

import com.neurotech.data.modules.storage.database.entity.ResultEntity

data class ResultFirebase(
    val time: String? = null,
    val peakCount: Int? = null,
    val tonicAvg: Int? = null,
    val conditionAssessment: Int? = null,
    val stressCause: String? = null,
    val keep: String? = null
){
    fun toResultEntity():ResultEntity{
        return ResultEntity(
            time!!,
            peakCount!!,
            tonicAvg!!,
            conditionAssessment!!,
            stressCause,
            keep
        )
    }
}