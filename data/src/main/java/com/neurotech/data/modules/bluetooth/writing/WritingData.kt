package com.neurotech.data.modules.bluetooth.writing

import java.util.*

interface WritingData {
    suspend fun writeTime(time: Date)
    suspend fun writePeaks(peaks: Int)
    suspend fun writeTonic(value: Int)
}