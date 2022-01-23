package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.domain.repository.MainFunctions
import javax.inject.Inject

class MainFunctionsImpl: MainFunctions {

    @Inject
    lateinit var bleConnection: BleConnection
    var tonicValue = MutableLiveData<Int>()

    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun getDeviceState(): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun disconnectDevice() {
        bleConnection.disconnectDevice()
    }
}