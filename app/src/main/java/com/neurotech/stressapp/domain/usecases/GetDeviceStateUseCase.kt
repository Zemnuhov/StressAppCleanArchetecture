package com.neurotech.stressapp.domain.usecases

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.polidea.rxandroidble2.RxBleConnection

class GetDeviceStateUseCase(private val connectionRepository: ConnectionRepository) {
    fun getDeviceState(): LiveData<RxBleConnection.RxBleConnectionState> {
        return connectionRepository.getDeviceState()
    }
}