package com.neurotech.domain.repository

import java.util.*

interface RecodingInDevice {
    suspend fun recodingPeaks(peaks: Int)
    suspend fun recodingTonic(value: Int)
    suspend fun recodingTime(time: Date)
}