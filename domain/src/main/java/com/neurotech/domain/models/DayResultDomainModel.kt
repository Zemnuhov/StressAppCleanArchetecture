package com.neurotech.domain.models

import java.util.Date

data class DayResultDomainModel(
    val date: Date,
    val peaks: Int,
    val peaksAvg: Int,
    val tonic: Int,
    val stressCause: String
)