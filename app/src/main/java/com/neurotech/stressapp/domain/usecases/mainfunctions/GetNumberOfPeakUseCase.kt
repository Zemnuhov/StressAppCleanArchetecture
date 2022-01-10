package com.neurotech.stressapp.domain.usecases.mainfunctions

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetNumberOfPeakUseCase(private val mainFunctions: MainFunctions) {
    fun getNumberOfPeak(timeInterval:Long):LiveData<Int>{
        return mainFunctions.getNumberOfPeak(timeInterval)
    }
}