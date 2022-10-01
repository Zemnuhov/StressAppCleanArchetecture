package com.neurotech.stressapp.ui.Search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.connection.*
import javax.inject.Inject

class SearchFragmentViewModelFactory @Inject constructor(
    private val getDeviceListFlow: GetDeviceListFlow,
    private val getConnectionStateFlow: GetConnectionStateFlow,
    private val getScanState: GetScanState,
    private val connectionToPeripheral: ConnectionToPeripheral,
    private val stopScan: StopScan
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchFragmentViewModel(
            getDeviceListFlow,
            getConnectionStateFlow,
            getScanState,
            connectionToPeripheral,
            stopScan
        ) as T
    }
}