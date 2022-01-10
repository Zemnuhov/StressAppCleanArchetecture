package com.neurotech.stressapp.domain.usecases.connection

import com.neurotech.stressapp.domain.repository.ConnectionRepository

class SearchDeviceUseCase(private val connectionRepository: ConnectionRepository) {
    fun searchDevice(){
        connectionRepository.searchDevice()
    }
}