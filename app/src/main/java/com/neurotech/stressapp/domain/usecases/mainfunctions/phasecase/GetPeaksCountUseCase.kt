package com.neurotech.stressapp.domain.usecases.mainfunctions.phasecase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.PhaseCaseRepository

class GetPeaksCountUseCase(val repository: PhaseCaseRepository) {
    operator fun invoke():LiveData<Int>{
        return repository.getPeakCount()
    }
}