package com.neurotech.domain.models

data class UserParameterDomainModel(
    val peaksInDay: Int,
    val peaksInTenMinute: Int,
    val tonicAverage: Int,
)