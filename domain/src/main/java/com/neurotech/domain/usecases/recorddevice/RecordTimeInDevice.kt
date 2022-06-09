package com.neurotech.domain.usecases.recorddevice

import com.neurotech.domain.repository.RecodingInDevice
import java.util.*

class RecordTimeInDevice(private val repository: RecodingInDevice) {
    suspend operator fun invoke(time: Date){
        repository.recodingTime(time)
    }
}