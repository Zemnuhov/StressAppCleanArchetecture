package com.neurotech.domain.usecases.gsrdata

import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.repository.GsrDataRepository
import kotlinx.coroutines.flow.Flow

class GetPhaseValueFlow(private val gsrDataRepository: GsrDataRepository) {
    suspend operator fun invoke(): Flow<PhaseFlowDomainModel>{
        return gsrDataRepository.getPhaseValueFlow()
    }
}