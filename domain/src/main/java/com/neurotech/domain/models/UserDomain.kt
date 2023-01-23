package com.neurotech.domain.models

import java.util.*

data class UserDomain(
    val id:String,
    val name: String,
    val dateOfBirth: Date?,
    val gender: String?,
    val tonicAvg: Int,
    val peakInDayNormal:Int,
    val peakInHourNormal:Int,
    val peakNormal:Int
)
