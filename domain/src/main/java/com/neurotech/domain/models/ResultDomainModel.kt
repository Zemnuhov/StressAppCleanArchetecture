package com.neurotech.domain.models

data class ResultDomainModel(
    val time: String,
    val peakCount: Int,
    val tonicAvg: Int,
    val conditionAssessment: Int,
    var stressCause: String? = null,
)