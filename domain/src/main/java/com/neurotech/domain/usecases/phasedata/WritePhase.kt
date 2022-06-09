package com.neurotech.domain.usecases.phasedata

import com.neurotech.domain.models.PeakDomainModel
import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.repository.PhaseRepository
import kotlinx.coroutines.flow.Flow

class WritePhase (private val repository: PhaseRepository) {
    suspend operator fun invoke(model: PeakDomainModel){
        repository.writePhase(model)
    }
}