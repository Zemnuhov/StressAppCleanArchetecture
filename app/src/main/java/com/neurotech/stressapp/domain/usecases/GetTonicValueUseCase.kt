package com.neurotech.stressapp.domain.usecases

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions

class GetTonicValueUseCase(private val mainFunctions: MainFunctions) {
    fun getTonicValue():LiveData<Int>{
        return mainFunctions.getTonicValue()
    }
}