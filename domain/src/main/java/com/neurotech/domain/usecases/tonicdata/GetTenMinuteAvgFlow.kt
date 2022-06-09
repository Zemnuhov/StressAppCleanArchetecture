package com.neurotech.domain.usecases.tonicdata

import com.neurotech.domain.repository.TonicRepository
import kotlinx.coroutines.flow.Flow

class GetTenMinuteAvgFlow(private val repository: TonicRepository) {
    suspend operator fun invoke(): Flow<Int?> {
        return repository.getTenMinuteAvgFlow()
    }
}