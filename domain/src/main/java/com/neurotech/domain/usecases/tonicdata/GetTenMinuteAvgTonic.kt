package com.neurotech.domain.usecases.tonicdata

import com.neurotech.domain.repository.TonicRepository
import kotlinx.coroutines.flow.Flow

class GetTenMinuteAvgTonic(private val repository: TonicRepository) {
    suspend operator fun invoke(): Int {
        return repository.getTenMinuteAvg()
    }
}