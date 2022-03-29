package com.neurotech.test.bluetooth.data

import android.content.Context
import com.neurotech.test.App
import com.neurotech.test.bluetooth.Bluetooth
import com.neurotech.test.bluetooth.data.filters.ExpRunningAverage
import com.neurotech.test.bluetooth.data.filters.KalmanFilter
import com.neurotech.test.bluetooth.data.filters.ValueConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class GsrDataBluetooth(context: Context) : GsrData {

    @Inject
    lateinit var bluetooth: Bluetooth
    private val tonicFlow = MutableSharedFlow<TonicModelBluetooth>()
    private val phaseFlow = MutableSharedFlow<PhaseModelBluetooth>()
    private val valueConverter = ValueConverter()

    init {
        (context as App).component.inject(this)
    }

    override suspend fun getTonicValueFlow(): Flow<TonicModelBluetooth> {
        val kalmanFilter = KalmanFilter(0.0,0.1)
        val expRunningAverage = ExpRunningAverage(0.01)
        bluetooth.peripheral.getCharacteristic(bluetooth.serviceUUID, bluetooth.notificationDataUUID)?.let {
            bluetooth.peripheral.observe(it){ value ->
                var data = ByteBuffer.wrap(value).int
                data = valueConverter.rangeConvert(data)
                data = kalmanFilter.correct(data).toInt()
                data = expRunningAverage.filter(data).toInt()
                CoroutineScope(Dispatchers.IO).launch {
                    tonicFlow.emit(TonicModelBluetooth(data, Date()))
                }
            }
        }
        return tonicFlow
    }

    override suspend fun getPhaseValueFlow(): Flow<PhaseModelBluetooth> {
        val kalmanFilter = KalmanFilter(0.0, 0.1)
        val expRunningAverage = ExpRunningAverage(0.01)
        val expRunningAverageTonic = ExpRunningAverage(0.1)
        bluetooth.peripheral.getCharacteristic(bluetooth.serviceUUID, bluetooth.notificationDataUUID)?.let {
            bluetooth.peripheral.observe(it){ value ->
                var data = ByteBuffer.wrap(value).int
                data = valueConverter.rangeConvert(data)
                data = expRunningAverageTonic.filter(data).toInt()
                data = valueConverter.toPhaseValue(data)
                var phaseValue = kalmanFilter.correct(data)
                phaseValue = expRunningAverage.filter(phaseValue)
                CoroutineScope(Dispatchers.IO).launch {
                    phaseFlow.emit(PhaseModelBluetooth(phaseValue,Date()))
                }
            }
        }
        return phaseFlow
    }
}