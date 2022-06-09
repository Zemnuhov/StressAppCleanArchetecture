package com.neurotech.data.modules.bluetooth.data

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.neurotech.data.modules.bluetooth.data.filters.ExpRunningAverage
import com.neurotech.data.modules.bluetooth.data.filters.KalmanFilter
import com.neurotech.data.modules.bluetooth.data.filters.ValueConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class GsrDataBluetooth : GsrData {

//    @Inject
//    lateinit var bluetooth: Bluetooth
    private val tonicFlow = MutableStateFlow(TonicModelBluetooth(0,Date()))
    private val phaseFlow = MutableStateFlow(PhaseModelBluetooth(0.0, Date()))
//    private val valueConverter = ValueConverter()
//    private val clearDataFlow = MutableSharedFlow<Int>()
//    private var phaseJob: Job? = null
//    private var tonicJob: Job? = null
//
//    init {
//        component.inject(this)
//        CoroutineScope(Dispatchers.Default).launch {
//            getBleDataFlow()
//        }
//    }

    private suspend fun getBleDataFlow() {
//        bluetooth.peripheral.getCharacteristic(
//            bluetooth.serviceUUID,
//            bluetooth.notificationDataUUID
//        )?.let {
//            bluetooth.peripheral.observe(it) { value ->
//                var data = ByteBuffer.wrap(value).int
//                data = valueConverter.rangeConvert(data)
//                CoroutineScope(Dispatchers.IO).launch {
//                    clearDataFlow.emit(data)
//                }
//            }
//        }
    }


    override suspend fun getTonicValueFlow(): SharedFlow<TonicModelBluetooth> {
//        val kalmanFilter = KalmanFilter(0.0, 0.1)
//        val expRunningAverage = ExpRunningAverage(0.1)
//        if (tonicJob == null) {
//            tonicJob = CoroutineScope(Dispatchers.Default).launch {
//                clearDataFlow.collect {
//                    var data = kalmanFilter.correct(it).toInt()
//                    data = expRunningAverage.filter(data).toInt()
//                    tonicFlow.value = TonicModelBluetooth(data, Date())
//                }
//            }
//        }
        return tonicFlow
    }


    override suspend fun getPhaseValueFlow(): StateFlow<PhaseModelBluetooth> {
//        val kalmanFilter = KalmanFilter(0.0, 0.1)
//        val expRunningAverage = ExpRunningAverage(0.1)
//        val expRunningAverageTonic = ExpRunningAverage(0.1)
//        if (phaseJob == null) {
//            phaseJob = CoroutineScope(Dispatchers.Default).launch {
//                clearDataFlow.collect {
//                    var data = expRunningAverageTonic.filter(it).toInt()
//                    data = valueConverter.toPhaseValue(data)
//                    var phaseValue = kalmanFilter.correct(data)
//                    phaseValue = expRunningAverage.filter(phaseValue)
//                    phaseFlow.value = PhaseModelBluetooth(phaseValue, Date())
//                }
//            }
//        }
        return phaseFlow
    }
}


