package com.neurotech.stressapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.neurotech.stressapp.domain.usecases.GetDeviceStateUseCase
import com.neurotech.stressapp.domain.usecases.connection.ConnectionToDeviceUseCase
import com.neurotech.stressapp.domain.usecases.connection.GetListDeviceUseCase
import com.neurotech.stressapp.domain.usecases.connection.GetSearchStateUseCase
import com.polidea.rxandroidble2.RxBleConnection
import javax.inject.Inject

class SearchFragmentViewModel: ViewModel() {

    @Inject
    lateinit var repository: ConnectionRepository
    val deviceList:LiveData<List<Device>>
    val deviceState: LiveData<RxBleConnection.RxBleConnectionState>
    val searchState: LiveData<Boolean>

    init {
        Singleton.daggerComponent.inject(this)
        deviceList = GetListDeviceUseCase(repository).getListDevice()
        deviceState = GetDeviceStateUseCase(repository).getDeviceState()
        searchState = GetSearchStateUseCase(repository).getSearchState()
    }

    fun connectionToDevice(MAC: String){
        ConnectionToDeviceUseCase(repository).connectionToDevice(MAC)
    }
}