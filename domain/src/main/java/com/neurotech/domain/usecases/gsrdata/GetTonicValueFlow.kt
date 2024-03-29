package com.neurotech.domain.usecases.gsrdata

import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.repository.GsrDataRepository
import kotlinx.coroutines.flow.Flow

class GetTonicValueFlow(private val gsrDataRepository: GsrDataRepository) {
    suspend operator fun invoke(): Flow<TonicFlowDomainModel> {
        return gsrDataRepository.getTonicValueFlow()
    }
}