package com.neurotech.stressapp.ui.main.MainHost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.connection.ConnectionToPeripheral
import com.neurotech.domain.usecases.connection.DisconnectDevice
import com.neurotech.domain.usecases.connection.GetConnectionState
import com.neurotech.domain.usecases.connection.GetConnectionStateFlow
import com.neurotech.domain.usecases.recorddevice.RecordNotifyFlag
import com.neurotech.domain.usecases.settings.GetDeviceInMemory
import javax.inject.Inject

class MainFragmentViewModelFactory @Inject constructor(
    private val disconnectDevice: DisconnectDevice,
    private val connectionStateUseCase: GetConnectionStateFlow,
    private val getConnectionState: GetConnectionState,
    private val connectionToPeripheral: ConnectionToPeripheral,
    private val getDeviceInMemory: GetDeviceInMemory,
    private val recordNotifyFlag: RecordNotifyFlag
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(
            disconnectDevice,
            connectionStateUseCase,
            getConnectionState,
            connectionToPeripheral,
            getDeviceInMemory,
            recordNotifyFlag
        ) as T
    }
}