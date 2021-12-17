package com.neurotech.stressapp.domain.usecases

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetAvgTonicValueUseCase(private val mainFunctions: MainFunctions) {
    fun getAvgTonicValue(timeInterval:Long):LiveData<Int>{
        return mainFunctions.getAvgTonicValue(timeInterval)
    }
}