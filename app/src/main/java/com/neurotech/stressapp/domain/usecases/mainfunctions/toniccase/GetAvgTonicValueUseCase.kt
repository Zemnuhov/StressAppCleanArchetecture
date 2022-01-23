package com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.neurotech.stressapp.domain.repository.TonicCaseRepository

class GetAvgTonicValueUseCase(private val tonicCaseRepository: TonicCaseRepository) {
    fun getAvgTonicValue():LiveData<Int>{
        return tonicCaseRepository.getAvgTonicValue()
    }
}