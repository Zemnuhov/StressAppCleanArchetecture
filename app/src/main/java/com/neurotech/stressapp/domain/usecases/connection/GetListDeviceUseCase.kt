package com.neurotech.stressapp.domain.usecases.connection

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetListDeviceUseCase(private val connectionRepository: ConnectionRepository) {
    fun getListDevice(): LiveData<List<Device>>{
        return connectionRepository.getListDevice()
    }
}