package com.neurotech.domain.usecases.recorddevice

import com.neurotech.domain.repository.RecodingInDevice

class RecordPeaksInDevice(private val repository: RecodingInDevice) {
    suspend operator fun invoke(peaks: Int){
        repository.recodingPeaks(peaks)
    }
}