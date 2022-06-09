package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.repository.ResultDataRepository

class SetStressCauseByTime(private val repository: ResultDataRepository) {
    suspend operator fun invoke(stressCause: String, time: List<String>) {
        repository.setStressCauseByTime(stressCause, time)
    }
}