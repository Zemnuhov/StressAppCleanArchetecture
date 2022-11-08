package com.neurotech.domain.usecases.tonicdata

import com.neurotech.domain.repository.TonicRepository

class GetTenMinuteAvgTonic(private val repository: TonicRepository) {
    suspend operator fun invoke(): Int {
        return repository.getTenMinuteAvg()
    }
}