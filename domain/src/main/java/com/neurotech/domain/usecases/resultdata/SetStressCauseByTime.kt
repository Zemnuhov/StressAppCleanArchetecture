package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.repository.ResultDataRepository
import java.util.*

class SetStressCauseByTime(private val repository: ResultDataRepository) {
    suspend operator fun invoke(stressCause: String, time: List<Date>) {
        repository.setStressCauseByTime(stressCause, time)
    }
}