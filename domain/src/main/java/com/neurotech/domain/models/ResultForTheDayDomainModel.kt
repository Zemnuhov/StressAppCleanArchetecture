package com.neurotech.domain.models

import java.util.*

data class ResultForTheDayDomainModel(
    val date: Date,
    val peaks: Int,
    val tonic: Int,
    val stressCause: String
)
