package com.neurotech.stressapp.domain.usecases

import com.neurotech.stressapp.domain.repository.MainFunctions

class GetDeviceStateUseCase(private val mainFunctions: MainFunctions) {
    fun getDeviceState(): String{
        return mainFunctions.getDeviceState()
    }
}