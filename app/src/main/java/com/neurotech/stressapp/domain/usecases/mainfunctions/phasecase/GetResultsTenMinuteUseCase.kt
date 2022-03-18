package com.neurotech.stressapp.domain.usecases.mainfunctions.phasecase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.ResultEntity
import com.neurotech.stressapp.data.database.entity.ResultTimeAndPeak
import com.neurotech.stressapp.domain.repository.PhaseCaseRepository

class GetResultsTenMinuteUseCase(val repository: PhaseCaseRepository) {
    operator fun invoke(): LiveData<List<ResultTimeAndPeak>>{
        return repository.getResultsTenMinute()
    }
}