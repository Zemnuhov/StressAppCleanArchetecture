package com.neurotech.stressapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neurotech.domain.models.DeviceDomainModel
import com.neurotech.domain.usecases.connection.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchFragmentViewModel(
    private val getDeviceListFlow: GetDeviceListFlow,
    private val getConnectionStateFlow: GetConnectionStateFlow,
    private val getScanState: GetScanState,
    private val connectionToPeripheral: ConnectionToPeripheral,
    private val stopScan: StopScan
) : ViewModel() {

    private val _deviceList = MutableLiveData<List<DeviceDomainModel>>()
    val deviceList: LiveData<List<DeviceDomainModel>> get() = _deviceList

    private val _deviceState = MutableLiveData<String>()
    val deviceState: LiveData<String> get() = _deviceState

    private val _searchState = MutableLiveData<Boolean>()
    val searchState: LiveData<Boolean> = _searchState

    private val scope = CoroutineScope(Dispatchers.Default)
    private val jobs = mutableListOf<Job>()

    init {
        Log.e("AAA", "ViewModel Init")
        jobs.add(
            scope.launch {
                launch {
                    getConnectionStateFlow.invoke().collect {
                        _deviceState.postValue(it)
                    }
                }
            }
        )

        jobs.add(
            scope.launch{
                getScanState.invoke().collect {
                    Log.e("AAA", it.toString())
                    _searchState.postValue(it)
                }
            }
        )

    }

    fun connectionToDevice(MAC: String) {
        jobs.add(
            scope.launch {
                connectionToPeripheral(MAC)
            }
        )
    }

    fun searchDevice() {
        jobs.add(
            scope.launch {
                getDeviceListFlow.invoke().collect {
                    _deviceList.postValue(it)
                }
            }
        )
    }

    fun stopScan(){
        scope.launch {
            stopScan.invoke()
        }
    }

    override fun onCleared() {
        jobs.forEach { it.cancel() }
    }
}