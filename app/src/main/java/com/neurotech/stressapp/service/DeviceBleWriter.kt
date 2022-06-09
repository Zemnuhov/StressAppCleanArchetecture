package com.neurotech.stressapp.service

import com.neurotech.domain.usecases.recorddevice.RecordPeaksInDevice
import com.neurotech.domain.usecases.recorddevice.RecordTimeInDevice
import com.neurotech.domain.usecases.recorddevice.RecordTonicInDevice
import java.util.*
import javax.inject.Inject

class DeviceBleWriter(
    private val recordPeaksInDevice: RecordPeaksInDevice,
    private val recordTonicInDevice: RecordTonicInDevice,
    private val recordTimeInDevice: RecordTimeInDevice
) {

    suspend fun recordAll(peaks: Int, tonicAvg: Int, time: Date){
        recordPeaks(peaks)
        recordTonic(tonicAvg)
        recordTime(time)
    }

    suspend fun recordPeaks(peaks: Int){
        recordPeaksInDevice.invoke(peaks)
    }

    suspend fun recordTonic(value: Int){
        recordTonicInDevice.invoke(value)
    }

    suspend fun recordTime(time: Date){
        recordTimeInDevice.invoke(time)
    }


}