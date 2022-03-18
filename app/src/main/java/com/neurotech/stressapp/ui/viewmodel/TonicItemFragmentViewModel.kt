package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.domain.repository.GraphRepository
import com.neurotech.stressapp.domain.repository.TonicCaseRepository
import com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase.GetAvgTonicValueUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase.GetTonicValueUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase.SetIntervalUseCase
import javax.inject.Inject

class TonicItemFragmentViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: TonicCaseRepository
    val tonicValue: LiveData<Int>
    val avgTonic: LiveData<Int>

    init {
        (application as App).component.inject(this)
        tonicValue = GetTonicValueUseCase(repository).getTonicValue()
        avgTonic = GetAvgTonicValueUseCase(repository).getAvgTonicValue()
    }

    fun setInterval(interval: String){
        SetIntervalUseCase(repository).setInterval(interval)
    }
}