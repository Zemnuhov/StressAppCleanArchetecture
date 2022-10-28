package com.neurotech.domain.usecases.phasedata

import com.neurotech.domain.repository.PhaseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class GetPeaksInInterval(private val repository: PhaseRepository) {
    suspend operator fun invoke(dateTime: Date): Flow<Int> {
        return repository.getPeaksInInterval(dateTime)
    }
}