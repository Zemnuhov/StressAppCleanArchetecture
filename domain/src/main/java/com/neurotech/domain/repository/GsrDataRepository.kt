package com.neurotech.domain.repository

import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.models.TonicFlowDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface GsrDataRepository {
    suspend fun getTonicValueFlow(): SharedFlow<TonicFlowDomainModel>
    suspend fun getPhaseValueFlow(): SharedFlow<PhaseFlowDomainModel>
}