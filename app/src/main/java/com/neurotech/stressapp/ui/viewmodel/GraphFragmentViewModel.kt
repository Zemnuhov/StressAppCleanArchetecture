package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.domain.repository.GraphRepository
import com.neurotech.stressapp.domain.usecases.mainfunctions.graphcase.GetPhaseValueUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.graphcase.GetThresholdUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.graphcase.GetTonicValueUseCase
import javax.inject.Inject

class GraphFragmentViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: GraphRepository
    val phaseValue : LiveData<HashMap<String,Any>>
    val threshold : Double

    init {
        (application as App).component.inject(this)
        phaseValue = GetPhaseValueUseCase(repository).getPhaseValue()
        threshold = GetThresholdUseCase(repository).getThreshold()
    }
}