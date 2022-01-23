package com.neurotech.stressapp.domain.usecases.mainfunctions.graphcase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.GraphRepository

class GetPhaseValueUseCase(private val repository: GraphRepository) {
    fun getPhaseValue():LiveData<HashMap<String,Any>>{
        return repository.getPhaseValue()
    }
}