package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.Device
import com.polidea.rxandroidble2.RxBleConnection

interface ConnectionRepository {
    fun getDeviceState(): LiveData<String>
    fun getListDevice(): LiveData<List<Device>>
    fun connectionToDevice(MAC: String)
    fun searchState(): LiveData<Boolean>
    fun searchDevice()
}