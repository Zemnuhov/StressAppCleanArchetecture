package com.neurotech.domain.usecases.recorddevice

import com.neurotech.domain.repository.RecodingInDevice
import com.neurotech.domain.repository.TonicRepository

class RecordPeaksInDevice(private val repository: RecodingInDevice) {
    suspend operator fun invoke(peaks: Int){
        repository.recodingPeaks(peaks)
    }
}