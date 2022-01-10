package com.neurotech.stressapp.domain.usecases.mainfunctions

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetValueForGraphTonicUseCase(private val mainFunctions: MainFunctions) {
    fun getValueForGraphTonic(): LiveData<Float> {
        return mainFunctions.getValueForGraphTonic()
    }
}