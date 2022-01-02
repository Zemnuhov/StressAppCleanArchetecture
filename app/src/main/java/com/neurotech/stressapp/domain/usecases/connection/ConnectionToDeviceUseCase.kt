package com.neurotech.stressapp.domain.usecases.connection

import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.neurotech.stressapp.domain.repository.MainFunctions

class ConnectionToDeviceUseCase(private val connectionRepository: ConnectionRepository) {
    fun connectionToDevice(MAC: String){
        connectionRepository.connectionToDevice(MAC)
    }
}