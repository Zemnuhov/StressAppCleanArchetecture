package com.neurotech.stressapp.domain.usecases.mainfunctions.toniccase

import com.neurotech.stressapp.domain.repository.TonicCaseRepository

class SetIntervalUseCase(private val tonicCaseRepository: TonicCaseRepository) {
    fun setInterval(interval:String){
        tonicCaseRepository.setInterval(interval)
    }
}