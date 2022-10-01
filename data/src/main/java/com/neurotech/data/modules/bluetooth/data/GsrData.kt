package com.neurotech.data.modules.bluetooth.data

import com.neurotech.data.modules.bluetooth.data.dataflow.PhaseModelBluetooth
import com.neurotech.data.modules.bluetooth.data.dataflow.TonicModelBluetooth
import kotlinx.coroutines.flow.Flow

interface GsrData {
    suspend fun getTonicValueFlow(): Flow<TonicModelBluetooth>
    suspend fun getPhaseValueFlow(): Flow<PhaseModelBluetooth>
}