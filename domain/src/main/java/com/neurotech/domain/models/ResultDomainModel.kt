package com.neurotech.domain.models

import java.util.*

data class ResultDomainModel(
    val time: Date,
    val peakCount: Int,
    val tonicAvg: Int,
    val conditionAssessment: Int,
    var stressCause: String? = null,
)