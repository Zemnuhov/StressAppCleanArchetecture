package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.MaxParamDomainModel
import com.neurotech.domain.repository.DayResultRepository
import kotlinx.coroutines.Deferred

class GetMaxParam(private val repository: DayResultRepository) {
    suspend operator fun invoke(): MaxParamDomainModel {
        return repository.getMaxParamAsync().await()
    }
}