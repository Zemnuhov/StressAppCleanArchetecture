package com.neurotech.data.modules.bluetooth.data

import com.cesarferreira.tempo.Tempo
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataFlow: GsrData {
    @Inject
    lateinit var manager: AppBluetoothManager

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        RepositoryDI.component.inject(this)
    }

    override suspend fun getTonicValueFlow(): Flow<TonicModelBluetooth> {
        return manager.tonicValueFlow.map { TonicModelBluetooth(it, Tempo.now) }
    }

    override suspend fun getPhaseValueFlow(): Flow<PhaseModelBluetooth> {
        return manager.phaseValueFlow.map { PhaseModelBluetooth(it, Tempo.now) }
    }
}