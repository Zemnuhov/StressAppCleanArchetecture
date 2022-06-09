package com.neurotech.data.modules.bluetooth.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface GsrData {
    suspend fun getTonicValueFlow(): Flow<TonicModelBluetooth>
    suspend fun getPhaseValueFlow(): Flow<PhaseModelBluetooth>
}