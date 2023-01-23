package com.neurotech.domain.repository

import java.util.*

interface RecodingInDevice {
    suspend fun recodingTime(time: Date)
    suspend fun recodingNotifyFlag(isNotify: Boolean)
}