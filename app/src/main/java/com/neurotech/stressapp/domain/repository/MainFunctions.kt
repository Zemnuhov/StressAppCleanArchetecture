package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.SourceStressAndCountItem
import com.polidea.rxandroidble2.RxBleConnection

interface MainFunctions {

    fun getDeviceState(): LiveData<Int>

    fun disconnectDevice()

}