package com.neurotech.domain.usecases.phasedata

import com.neurotech.domain.repository.PhaseRepository
import kotlinx.coroutines.flow.Flow

class GetTenMinuteCountPhase(private val repository: PhaseRepository) {
    suspend operator fun invoke(): Int {
        return repository.getTenMinuteCount()
    }
}