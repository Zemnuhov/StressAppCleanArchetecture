package com.neurotech.stressapp.domain.usecases.mainfunctions.graphcase

import com.neurotech.stressapp.domain.repository.GraphRepository

class GetThresholdUseCase(private val repository: GraphRepository) {
    fun getThreshold():Double{
        return repository.getThreshold()
    }
}