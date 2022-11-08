package com.neurotech.data.modules.bluetooth.data

import com.cesarferreira.tempo.Tempo
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataFlow: GsrData {
    @Inject
    lateinit var manager: AppBluetoothManager

    val scope = CoroutineScope(Dispatchers.IO)

    private val _phaseValuesList = mutableListOf<PhaseModelBluetooth>()
    private val phaseValuesList: List<PhaseModelBluetooth> get() = _phaseValuesList

    private val _tonicValuesList = mutableListOf<TonicModelBluetooth>()
    private val tonicValuesList: List<TonicModelBluetooth> get() = _tonicValuesList

    init {
        RepositoryDI.component.inject(this)
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                manager.phaseValueFlow.collect{
                    if(_phaseValuesList.size<5000){
                        _phaseValuesList.add(PhaseModelBluetooth(it, Tempo.now))
                    }else{
                        _phaseValuesList.removeAt(0)
                        _phaseValuesList.add(PhaseModelBluetooth(it, Tempo.now))
                    }
                }
            }
            launch {
                manager.tonicValueFlow.collect{
                    if(_tonicValuesList.size<5000){
                        _tonicValuesList.add(TonicModelBluetooth(it,Tempo.now))
                    }else{
                        _tonicValuesList.removeAt(0)
                        _tonicValuesList.add(TonicModelBluetooth(it,Tempo.now))
                    }
                }
            }

        }
    }

    override suspend fun getTonicValueFlow(): Flow<TonicModelBluetooth> {
        return manager.tonicValueFlow.map { TonicModelBluetooth(it, Tempo.now) }
    }

    override suspend fun getPhaseValueFlow(): Flow<PhaseModelBluetooth> {
        return manager.phaseValueFlow.map { PhaseModelBluetooth(it, Tempo.now) }
    }

    override fun getPhaseValueInMemory(): List<PhaseModelBluetooth> {
        return phaseValuesList
    }

    override fun getTonicValueInMemory(): List<TonicModelBluetooth> {
        return tonicValuesList
    }
}