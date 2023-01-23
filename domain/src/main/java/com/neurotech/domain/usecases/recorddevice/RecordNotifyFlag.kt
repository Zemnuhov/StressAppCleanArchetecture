package com.neurotech.domain.usecases.recorddevice

import com.neurotech.domain.repository.RecodingInDevice
import java.util.*

class RecordNotifyFlag(private val repository: RecodingInDevice) {
    suspend operator fun invoke(isNotify: Boolean){
        repository.recodingNotifyFlag(isNotify)
    }
}