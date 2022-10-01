package com.neurotech.data.modules.bluetooth.syncdata

import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.test.storage.database.entity.PeakEntity
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import javax.inject.Inject

class SyncBlePeaks : SyncData {
    @Inject
    lateinit var bluetooth: Bluetooth

    @Inject
    lateinit var peaksDataBase: PeakDao

    private val scope = CoroutineScope(Dispatchers.IO)

    private var memoryFlowDisposable: Disposable? = null

    init {
        RepositoryDI.component.inject(this)
        scope.launch {
            bluetooth.connectionFlow.collect {
                if(it != null){
                    memoryFlowDisposable?.dispose()
                    memoryFlowDisposable = it.flatMap { rxBleConnection ->
                        rxBleConnection.setupNotification(
                            Bluetooth.memoryCharacteristic
                        )
                    }
                        .flatMap { v -> v }
                        .map { v -> ByteBuffer.wrap(v).int }
                        .subscribe(
                            { value ->
                                if (value == 1) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        writePeak()
                                    }
                                }
                            },
                            {},
                        )
                }
            }
        }


        scope.launch {
            val byteValue = ByteBuffer.allocate(4).putInt(1).array()
            byteValue.reverse()
            bluetooth.connectionFlow.collect{
                it?.flatMapSingle { rxBleConnection ->
                    rxBleConnection.writeCharacteristic(Bluetooth.memoryCharacteristic, byteValue)
                }?.subscribe(
                    {
                        appLog("Writing memoryCharacteristic in init: ${ByteBuffer.wrap(it.reversedArray()).int}")
                    },
                    {
                        appLog("Writing memoryCharacteristic error: $it")
                    }
                )
            }

        }
    }

    private fun sendWriteFlag(){
        val byteValue = ByteBuffer.allocate(4).putInt(1).array()
        byteValue.reverse()
        scope.launch {
            bluetooth.connectionFlow.collect{
                it?.flatMapSingle { rxBleConnection ->
                    rxBleConnection.writeCharacteristic(Bluetooth.memoryCharacteristic, byteValue)
                }?.subscribe(
                    { sentValue ->
                        appLog("Writing memoryCharacteristic: ${ByteBuffer.wrap(sentValue.reversedArray()).int}")
                    },
                    {
                        appLog("Writing memoryCharacteristic error: $it")
                    }
                )
            }
        }
    }

    private suspend fun writePeak() {
        bluetooth.connectionFlow.collect {
            it?.flatMapSingle { rxBleConnection ->
                Single.zip(
                    rxBleConnection.readCharacteristic(Bluetooth.memoryTimeBeginCharacteristic),
                    rxBleConnection.readCharacteristic(Bluetooth.memoryTimeEndCharacteristic),
                    rxBleConnection.readCharacteristic(Bluetooth.memoryMaxPeakValueCharacteristic),
                    rxBleConnection.readCharacteristic(Bluetooth.memoryDateEndCharacteristic)
                ) { bTime, eTime, max, date ->

                    PeakEntity(
                        String(date) + " " + String(bTime),
                        String(date) + " " + String(eTime),
                        ByteBuffer.wrap(max).int.toDouble()
                    )
                }
            }?.subscribe { value ->
                appLog("Data in memory: ${value.timeBegin}, ${value.timeEnd}, ${value.max}")
                peaksDataBase.insertPeak(value)
                sendWriteFlag()
            }
        }
    }
}