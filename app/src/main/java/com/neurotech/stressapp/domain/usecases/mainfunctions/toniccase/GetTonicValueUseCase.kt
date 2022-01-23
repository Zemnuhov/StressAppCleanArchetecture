package com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.neurotech.stressapp.domain.repository.TonicCaseRepository

class GetTonicValueUseCase(private val tonicCaseRepository: TonicCaseRepository) {
    fun getTonicValue():LiveData<Int>{
        return tonicCaseRepository.getTonicValue()
    }
}