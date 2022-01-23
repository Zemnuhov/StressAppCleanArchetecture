package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.neurotech.stressapp.domain.usecases.mainfunctions.DisconnectDeviceUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase.GetTonicValueUseCase
import javax.inject.Inject

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: MainFunctions

    init {
        (application as App).component.inject(this)
    }

    fun disconnectDevice(){
        DisconnectDeviceUseCase(repository).disconnectDevice()
    }
}