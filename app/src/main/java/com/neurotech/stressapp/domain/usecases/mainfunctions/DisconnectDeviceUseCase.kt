package com.neurotech.stressapp.domain.usecases.mainfunctions

import com.neurotech.stressapp.domain.repository.MainFunctions

class DisconnectDeviceUseCase(private val mainFunctions: MainFunctions) {
    fun disconnectDevice(){
        mainFunctions.disconnectDevice()
    }
}