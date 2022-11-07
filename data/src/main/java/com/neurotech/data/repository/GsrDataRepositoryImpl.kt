package com.neurotech.data.repository

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.data.GsrData
import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.repository.GsrDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class GsrDataRepositoryImpl : GsrDataRepository {

    @Inject
    lateinit var dataFlow: GsrData
    private val tonicSharedFlow = MutableSharedFlow<TonicFlowDomainModel>()
    private val phaseSharedFlow = MutableSharedFlow<PhaseFlowDomainModel>()

    init {
        component.inject(this)
    }

    override suspend fun getTonicValueFlow(): SharedFlow<TonicFlowDomainModel> {
        CoroutineScope(Dispatchers.Default).launch {
            dataFlow.getTonicValueFlow().collect {
                tonicSharedFlow.emit(
                    TonicFlowDomainModel(it.value, it.time)
                )
            }
        }
        return tonicSharedFlow
    }

    override suspend fun getPhaseValueFlow(): SharedFlow<PhaseFlowDomainModel> {
        CoroutineScope(Dispatchers.Default).launch {
            dataFlow.getPhaseValueFlow().collect {
                phaseSharedFlow.emit(
                    PhaseFlowDomainModel(it.value, it.time)
                )
            }
        }
        return phaseSharedFlow
    }

    override fun getPhaseValueInMemory(): List<PhaseFlowDomainModel> {
        return dataFlow.getPhaseValueInMemory().map { PhaseFlowDomainModel(it.value, it.time) }
    }

    override fun getTonicValueInMemory(): List<TonicFlowDomainModel> {
        return dataFlow.getTonicValueInMemory().map { TonicFlowDomainModel(it.value, it.time) }
    }
}