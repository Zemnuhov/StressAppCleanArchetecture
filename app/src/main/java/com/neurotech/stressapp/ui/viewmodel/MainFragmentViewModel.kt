package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.neurotech.stressapp.domain.usecases.mainfunctions.DisconnectDeviceUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.GetTonicValueUseCase
import javax.inject.Inject

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: MainFunctions
    var tonicValue: LiveData<Int>

    init {
        (application as App).component.inject(this)
        tonicValue = GetTonicValueUseCase(repository).getTonicValue()
    }

    fun disconnectDevice(){
        DisconnectDeviceUseCase(repository).disconnectDevice()
    }
}