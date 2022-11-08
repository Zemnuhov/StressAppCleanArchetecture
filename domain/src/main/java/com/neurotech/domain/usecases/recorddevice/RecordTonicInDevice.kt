package com.neurotech.domain.usecases.recorddevice

import com.neurotech.domain.repository.RecodingInDevice

class RecordTonicInDevice(private val repository: RecodingInDevice) {
    suspend operator fun invoke(value: Int){
        repository.recodingTonic(value)
    }
}