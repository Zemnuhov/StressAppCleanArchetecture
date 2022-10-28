package com.neurotech.data.modules.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.content.Context
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.modules.bluetooth.ListUUID.dataServiceUUID
import com.neurotech.data.modules.bluetooth.ListUUID.dateUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryCharacteristicUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryDateEndCharacteristicUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryMaxPeakValueCharacteristicUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryServiceUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryTimeBeginCharacteristicUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryTimeEndCharacteristicUUID
import com.neurotech.data.modules.bluetooth.ListUUID.memoryTonicCharacteristicUUID
import com.neurotech.data.modules.bluetooth.ListUUID.phaseFlowUUID
import com.neurotech.data.modules.bluetooth.ListUUID.timeUUID
import com.neurotech.data.modules.bluetooth.ListUUID.tonicFlowUUID
import com.neurotech.data.modules.bluetooth.data.filters.ExpRunningAverage
import com.neurotech.test.storage.database.entity.PeakEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.PhyRequest
import no.nordicsemi.android.ble.ktx.asFlow
import no.nordicsemi.android.ble.ktx.stateAsFlow
import no.nordicsemi.android.ble.ktx.suspend
import java.nio.ByteBuffer

@Suppress("OPT_IN_IS_NOT_ENABLED")
class AppBluetoothManager(context: Context) : BleManager(context) {
    private var phaseFlowCharacteristic: BluetoothGattCharacteristic? = null
    private var tonicFlowCharacteristic: BluetoothGattCharacteristic? = null
    private var timeCharacteristic: BluetoothGattCharacteristic? = null
    private var dateCharacteristic: BluetoothGattCharacteristic? = null
    private var characteristicServiceCharacteristic: BluetoothGattCharacteristic? = null

    private var memoryCharacteristic: BluetoothGattCharacteristic? = null
    private var memoryTimeBeginCharacteristic: BluetoothGattCharacteristic? = null
    private var memoryTimeEndCharacteristic: BluetoothGattCharacteristic? = null
    private var memoryDateEndCharacteristic: BluetoothGattCharacteristic? = null
    private var memoryMaxPeakValueCharacteristic: BluetoothGattCharacteristic? = null
    private var memoryTonicCharacteristic: BluetoothGattCharacteristic? = null
    val scope = CoroutineScope(Dispatchers.IO)

    private val _phaseValueFlow = MutableSharedFlow<Double>()
    private val _tonicValueFlow = MutableSharedFlow<Int>()
    private val _memoryStateFlow = MutableSharedFlow<Int>()

    val phaseValueFlow: Flow<Double> get() = _phaseValueFlow
    val tonicValueFlow: Flow<Int> get() = _tonicValueFlow
    val memoryStateFlow: Flow<Int> get() = _memoryStateFlow

    private val delay = 40L


    override fun getGattCallback(): BleManagerGattCallback {
        return MyGattCallbackImpl()
    }

    init {
        scope.launch {
            stateAsFlow().collect {
                if (it.isReady) {
                    observeNotification()
                }
            }
        }
    }

    private inner class MyGattCallbackImpl : BleManagerGattCallback() {
        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val dataService = gatt.getService(dataServiceUUID)
            val memoryService = gatt.getService(memoryServiceUUID)
            var dataCharacteristicResult = false
            var memoryCharacteristicResult = false
            if (dataService != null && memoryService != null) {
                dataCharacteristicResult = dataCharacteristicInit(dataService)
                memoryCharacteristicResult = memoryCharacteristicInit(memoryService)
            }
            return dataCharacteristicResult && memoryCharacteristicResult
        }

        fun memoryCharacteristicInit(memoryService: BluetoothGattService): Boolean {
            memoryCharacteristic = memoryService.getCharacteristic(memoryCharacteristicUUID)
            memoryTimeBeginCharacteristic = memoryService.getCharacteristic(
                memoryTimeBeginCharacteristicUUID
            )
            memoryTimeEndCharacteristic = memoryService.getCharacteristic(
                memoryTimeEndCharacteristicUUID
            )
            memoryDateEndCharacteristic = memoryService.getCharacteristic(
                memoryDateEndCharacteristicUUID
            )
            memoryMaxPeakValueCharacteristic = memoryService.getCharacteristic(
                memoryMaxPeakValueCharacteristicUUID
            )
            memoryTonicCharacteristic = memoryService.getCharacteristic(
                memoryTonicCharacteristicUUID
            )
            return memoryCharacteristic != null &&
                    memoryTimeBeginCharacteristic != null &&
                    memoryTimeEndCharacteristic != null &&
                    memoryDateEndCharacteristic != null &&
                    memoryMaxPeakValueCharacteristic != null &&
                    memoryTonicCharacteristic != null
        }

        fun dataCharacteristicInit(dataService: BluetoothGattService): Boolean {
            phaseFlowCharacteristic = dataService.getCharacteristic(phaseFlowUUID)
            tonicFlowCharacteristic = dataService.getCharacteristic(tonicFlowUUID)
            timeCharacteristic = dataService.getCharacteristic(timeUUID)
            dateCharacteristic = dataService.getCharacteristic(dateUUID)

            appLog("$phaseFlowCharacteristic -- $tonicFlowCharacteristic -- $timeCharacteristic -- $dateCharacteristic -- $characteristicServiceCharacteristic")
            return phaseFlowCharacteristic != null &&
                    tonicFlowCharacteristic != null &&
                    timeCharacteristic != null &&
                    dateCharacteristic != null
        }

        override fun initialize() {
            requestMtu(517).enqueue()
        }

        override fun onServicesInvalidated() {
            phaseFlowCharacteristic = null
            tonicFlowCharacteristic = null
            timeCharacteristic = null
            dateCharacteristic = null
            characteristicServiceCharacteristic = null
            memoryCharacteristic = null
            memoryTimeBeginCharacteristic = null
            memoryTimeEndCharacteristic = null
            memoryDateEndCharacteristic = null
            memoryMaxPeakValueCharacteristic = null
            memoryTonicCharacteristic = null
        }
    }

    fun connectToDevice(device: BluetoothDevice) {
        connect(device)
            .retry(3, 100)
            .timeout(15_000)
            .useAutoConnect(true)
            .usePreferredPhy(PhyRequest.PHY_LE_1M_MASK or PhyRequest.PHY_LE_2M_MASK or PhyRequest.PHY_LE_CODED_MASK)
            .timeout(15_000)
            .enqueue()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeNotification() {
        val filter = ExpRunningAverage(0.1)
        enableNotifications(phaseFlowCharacteristic).enqueue()
        enableNotifications(tonicFlowCharacteristic).enqueue()
        enableNotifications(memoryCharacteristic).enqueue()
        writeMemoryFlag()
        scope.launch {
            setNotificationCallback(phaseFlowCharacteristic).asFlow()
                .collect {
                    val bytes = it.value
                    if (bytes != null) {
                        var value = (ByteBuffer.wrap(bytes).int).toDouble()
                        value = filter.filter(value)
                        _phaseValueFlow.emit(value)
                        delay(delay)
                    }
                }
        }
        scope.launch {
            setNotificationCallback(tonicFlowCharacteristic).asFlow()
                .collect {
                    val bytes = it.value
                    if (bytes != null) {
                        val value = ByteBuffer.wrap(bytes).int
                        _tonicValueFlow.emit(value)
                        delay(delay)
                    }
                }
        }

        scope.launch {
            setNotificationCallback(memoryCharacteristic).asFlow()
                .collect {
                    val bytes = it.value
                    bytes?.let { b ->
                        _memoryStateFlow.emit(ByteBuffer.wrap(b).int)
                    }
                }
        }
    }

    suspend fun getPeaks(): PeakEntity? {
        val timeBeginRequest = readCharacteristic(memoryTimeBeginCharacteristic)
            .with { device, data ->
                appLog(
                    "Read 'BeginTimePeak' from ${device.address}. Data: ${
                        data.value?.let {
                            String(
                                it
                            )
                        }
                    }"
                )
            }
            .suspend()
        val timeEndRequest = readCharacteristic(memoryTimeEndCharacteristic)
            .with { device, data ->
                appLog(
                    "Read 'EndTimePeak' from ${device.address}. Data: ${
                        data.value?.let {
                            String(
                                it
                            )
                        }
                    }"
                )
            }
            .suspend()
        val dateRequest = readCharacteristic(memoryDateEndCharacteristic)
            .with { device, data ->
                appLog(
                    "Read 'DatePeak' from ${device.address}. Data: ${
                        data.value?.let {
                            String(
                                it
                            )
                        }
                    }"
                )
            }
            .suspend()
        val maxRequest = readCharacteristic(memoryMaxPeakValueCharacteristic)
            .with { device, data ->
                appLog(
                    "Read 'MaxPeak' from ${device.address}. Data: ${
                        data.value?.let {
                            ByteBuffer.wrap(
                                it
                            ).int
                        }
                    }"
                )
            }
            .suspend()

        var timeBegin = timeBeginRequest.value?.let { String(it) }
        var timeEnd = timeEndRequest.value?.let { String(it) }
        val max = maxRequest.value?.let { ByteBuffer.wrap(it).int }
        val date = dateRequest.value?.let { String(it) }
        if (timeBegin != null && timeEnd != null && max != null) {
            timeBegin = "$date $timeBegin"
            timeEnd = "$date $timeEnd"
            return PeakEntity(timeBegin, timeEnd, max.toDouble())
        }
        return null
    }

    fun writeMemoryFlag() {
        val byteValue = ByteBuffer.allocate(4).putInt(1).array()
        byteValue.reverse()
        writeCharacteristic(
            memoryCharacteristic,
            byteValue,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        )
            .with { device, data ->
                appLog(
                    "Write memory flag to device ${device.address}. Data: ${
                        data.value?.let { ByteBuffer.wrap(it).int }}"
                )
            }
            .fail { device, status -> appLog("Write memory flag to device fail ${device.address}. Status: $status") }
            .enqueue()
    }

    fun writeDateTime(time: ByteArray, date: ByteArray) {
        writeCharacteristic(
            timeCharacteristic,
            time,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        )
            .with { device, data ->
                appLog(
                    "Write time to device ${device.address}. Data: ${
                        String(
                            data.value!!
                        )
                    }"
                )
            }
            .fail { device, status -> appLog("Write time to device fail ${device.address}. Status: $status") }
            .enqueue()
        writeCharacteristic(
            dateCharacteristic,
            date,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        )
            .with { device, data ->
                appLog(
                    "Write date to device ${device.address}. Data: ${
                        String(
                            data.value!!
                        )
                    }"
                )
            }
            .fail { device, status -> appLog("Write date to device fail ${device.address}. Status: $status") }
            .enqueue()
    }
}