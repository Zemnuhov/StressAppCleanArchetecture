package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.repository.ResultDataRepository

class GetTenMinuteResultCount(private val repository: ResultDataRepository) {
    suspend operator fun invoke(): Int {
        return repository.getResultsTenMinuteCount()
    }
}