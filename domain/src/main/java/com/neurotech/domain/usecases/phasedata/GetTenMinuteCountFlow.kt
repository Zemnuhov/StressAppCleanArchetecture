package com.neurotech.domain.usecases.phasedata

import com.neurotech.domain.repository.PhaseRepository
import kotlinx.coroutines.flow.Flow

class GetTenMinuteCountFlow(private val repository: PhaseRepository) {
    suspend operator fun invoke(): Flow<Int> {
        return repository.getTenMinuteCountFlow()
    }
}