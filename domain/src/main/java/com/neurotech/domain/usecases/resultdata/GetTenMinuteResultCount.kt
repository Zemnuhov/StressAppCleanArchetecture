package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow

class GetTenMinuteResultCount(private val repository: ResultDataRepository) {
    suspend operator fun invoke(): Int {
        return repository.getResultsTenMinuteCount()
    }
}