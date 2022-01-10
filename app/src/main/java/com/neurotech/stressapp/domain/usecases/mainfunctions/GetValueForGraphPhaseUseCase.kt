package com.neurotech.stressapp.domain.usecases.mainfunctions

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetValueForGraphPhaseUseCase(private val mainFunctions: MainFunctions) {
    fun getValueForGraphPhase(): LiveData<Float>{
        return mainFunctions.getValueForGraphPhase()
    }
}