package com.neurotech.stressapp.ui.main.MainHost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.BleConstant
import com.neurotech.domain.usecases.connection.ConnectionToPeripheral
import com.neurotech.domain.usecases.connection.DisconnectDevice
import com.neurotech.domain.usecases.connection.GetConnectionState
import com.neurotech.domain.usecases.connection.GetConnectionStateFlow
import com.neurotech.domain.usecases.recorddevice.RecordNotifyFlag
import com.neurotech.domain.usecases.settings.GetDeviceInMemory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class  MainFragmentViewModel(
    private val disconnectUseCase: DisconnectDevice,
    private val connectionStateUseCase: GetConnectionStateFlow,
    private val getConnectionState: GetConnectionState,
    private val connectionToPeripheral: ConnectionToPeripheral,
    private val getDeviceInMemory: GetDeviceInMemory,
    private val recordNotifyFlag: RecordNotifyFlag
) : ViewModel() {

    private val _connectionState = MutableLiveData<String>()
    val connectionState: LiveData<String> get() = _connectionState

    init {
        viewModelScope.launch {
            connectionStateUseCase.invoke().collect{
                _connectionState.postValue(it)
            }
        }
        viewModelScope.launch{
            if(getConnectionState.invoke() == BleConstant.DISCONNECTED){
                val macDevice = getDeviceInMemory.invoke()
                if(macDevice != null){
                    connectionToPeripheral.invoke(macDevice)
                }
            }
        }
        viewModelScope.launch {
            recordNotifyFlag.invoke(true)
        }
    }


    fun disconnectDevice(){
        viewModelScope.launch {
            disconnectUseCase.invoke()
        }
    }
}