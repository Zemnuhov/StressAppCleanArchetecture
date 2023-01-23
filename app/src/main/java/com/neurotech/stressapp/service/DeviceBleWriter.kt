package com.neurotech.stressapp.service


import com.neurotech.domain.usecases.recorddevice.RecordTimeInDevice
import java.util.*

class DeviceBleWriter(
    private val recordTimeInDevice: RecordTimeInDevice
) {

    suspend fun recordAll(peaks: Int, tonicAvg: Int, time: Date){
        recordTime(time)
    }

    suspend fun recordTime(time: Date){
        recordTimeInDevice.invoke(time)
    }


}