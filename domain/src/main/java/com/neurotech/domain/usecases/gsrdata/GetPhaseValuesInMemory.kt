package com.neurotech.domain.usecases.gsrdata

import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.repository.GsrDataRepository

class GetPhaseValuesInMemory(private val repository: GsrDataRepository) {
    operator fun invoke():List<PhaseFlowDomainModel>{
        return repository.getPhaseValueInMemory()
    }
}