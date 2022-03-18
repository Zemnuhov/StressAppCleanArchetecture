package com.neurotech.stressapp.domain.usecases.mainfunctions.phasecase

import com.neurotech.stressapp.domain.repository.PhaseCaseRepository

class SetIntervalUseCase(val repository: PhaseCaseRepository) {
    operator fun invoke(interval: String){
        repository.setInterval(interval)
    }
}