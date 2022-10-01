package com.neurotech.stressapp.ui.Main.MainHost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.connection.DisconnectDevice
import com.neurotech.domain.usecases.connection.GetConnectionStateFlow
import javax.inject.Inject

class MainFragmentViewModelFactory @Inject constructor(
    private val disconnectDevice: DisconnectDevice,
    private val connectionStateUseCase: GetConnectionStateFlow
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(
            disconnectDevice,
            connectionStateUseCase
        ) as T
    }
}