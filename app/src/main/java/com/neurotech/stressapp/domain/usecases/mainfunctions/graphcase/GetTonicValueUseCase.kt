package com.neurotech.stressapp.domain.usecases.mainfunctions.graphcase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.GraphRepository

class GetTonicValueUseCase(private val repository: GraphRepository) {
    fun getTonicValue():LiveData<HashMap<String,Any>>{
        return repository.getTonicValue()
    }
}