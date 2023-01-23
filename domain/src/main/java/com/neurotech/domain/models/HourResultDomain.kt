package com.neurotech.domain.models

data class HourResultDomain(
    val date: String,
    val peaks: Int,
    val peaksAvg: Int,
    val tonic: Int,
    val stressCause: String?
)