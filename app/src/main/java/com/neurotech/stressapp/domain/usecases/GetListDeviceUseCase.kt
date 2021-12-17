package com.neurotech.stressapp.domain.usecases

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetListDeviceUseCase(private val mainFunctions: MainFunctions) {
    fun getListDevice(): LiveData<List<Device>>{
        return mainFunctions.getListDevice()
    }
}