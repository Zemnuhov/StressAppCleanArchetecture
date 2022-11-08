package com.neurotech.stressapp.ui.Main.MainHost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.usecases.connection.DisconnectDevice
import com.neurotech.domain.usecases.connection.GetConnectionStateFlow
import kotlinx.coroutines.launch

class  MainFragmentViewModel(
    private val disconnectUseCase: DisconnectDevice,
    private val connectionStateUseCase: GetConnectionStateFlow
) : ViewModel() {

    private val _connectionState = MutableLiveData<String>()
    val connectionState: LiveData<String> get() = _connectionState

    init {
        viewModelScope.launch {
            connectionStateUseCase.invoke().collect{
                _connectionState.postValue(it)
            }
        }
    }


    fun disconnectDevice(){
        viewModelScope.launch {
            disconnectUseCase.invoke()
        }
    }
}