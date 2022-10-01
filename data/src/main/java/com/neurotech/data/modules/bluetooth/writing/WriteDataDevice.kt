package com.neurotech.data.modules.bluetooth.writing

import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.minute
import com.cesarferreira.tempo.toString
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.neurotech.domain.BleConstant
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class WriteDataDevice : WritingData {

    @Inject
    lateinit var bluetooth: Bluetooth

    private val timeFormat = "HH:mm:ss"
    private val dateFormat = "yyyy-MM-dd"
    private var lastWriteTime = Tempo.now - 5.minute

    var timeDisposable: Disposable? = null
    var dateDisposable: Disposable? = null
    var phaseDisposable: Disposable? = null
    var tonicDisposable: Disposable? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        component.inject(this)
        scope.launch {
            bluetooth.getConnectionFlow().collect{
                when(it){
                    BleConstant.CONNECTED -> {
                        dispose()
                        writeTime(Tempo.now)
                    }
                }
            }
        }
    }

    private fun dispose(){
        timeDisposable?.dispose()
        phaseDisposable?.dispose()
        tonicDisposable?.dispose()
    }


    override suspend fun writeTime(time: Date) {
        if(Tempo.now - 1.minute > lastWriteTime){
            lastWriteTime = Tempo.now
            val timeString = time.toString(timeFormat)
            val dateString = time.toString(dateFormat)
            CoroutineScope(Dispatchers.IO).launch {
                    val timeByteArray = timeString.toByteArray()
                    val dateByteArray = dateString.toByteArray()
                    if (timeByteArray.isNotEmpty() && dateByteArray.isNotEmpty()) {
                        timeDisposable = bluetooth.connectionFlow.value
                            .flatMapSingle { rxBleConnection ->
                                rxBleConnection.writeCharacteristic(Bluetooth.timeUUID, timeByteArray)
                            }
                            .subscribe(
                                { data ->
                                    appLog("Writing time: ${String(data)}")
                                },
                                { error ->
                                    appLog("Writing time error: $error")
                                }
                            )
                        dateDisposable = bluetooth.connectionFlow.value
                            .flatMapSingle { rxBleConnection ->
                                rxBleConnection.writeCharacteristic(Bluetooth.dateUUID, dateByteArray)
                            }
                            .subscribe(
                                { data ->
                                    appLog("Writing date: ${String(data)}")
                                },
                                { error ->
                                    appLog("Writing date error: $error")
                                }
                            )
                    }
            }
        }
    }

    override suspend fun writePeaks(peaks: Int) {
//        val byteValue = ByteBuffer.allocate(4).putInt(peaks).array()
//        byteValue.reverse()
//        phaseDisposable = bluetooth.connectionFlow.value
//            .flatMapSingle { rxBleConnection ->
//                rxBleConnection.writeCharacteristic(BluetoothRX.phaseFlowUUID, byteValue)
//            }
//            .subscribe(
//                {
//                    appLog( "Writing peaks: $it")
//                },
//                {
//                    appLog( "Writing peaks error: $it")
//                }
//            )

    }

    override suspend fun writeTonic(value: Int) {
//        val byteValue = ByteBuffer.allocate(4).putInt(value).array()
//        byteValue.reverse()
//        tonicDisposable = bluetooth.connectionFlow.value
//            .flatMapSingle { rxBleConnection ->
//                rxBleConnection.writeCharacteristic(BluetoothRX.tonicFlowUUID, byteValue)
//            }
//            .subscribe(
//                {
//                    appLog("Writing tonic: $it")
//                },
//                {
//                    appLog("Writing tonic error: $it")
//                }
//            )
    }
}