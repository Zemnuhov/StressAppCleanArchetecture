package com.neurotech.domain.usecases.phasedata

import com.neurotech.domain.models.PeakDomainModel
import com.neurotech.domain.repository.PhaseRepository

class WritePhase (private val repository: PhaseRepository) {
    suspend operator fun invoke(model: PeakDomainModel){
        repository.writePhase(model)
    }
}