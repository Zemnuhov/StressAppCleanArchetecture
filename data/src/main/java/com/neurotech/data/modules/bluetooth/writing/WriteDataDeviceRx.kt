package com.neurotech.data.modules.bluetooth.writing

import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.minute
import com.cesarferreira.tempo.toString
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.BluetoothRX
import com.neurotech.domain.BleConstant
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class WriteDataDeviceRx : WritingData {

    @Inject
    lateinit var bluetooth: BluetoothRX

    private val timeFormat = "HH:mm:ss"
    private var lastWriteTime = Tempo.now - 5.minute

    var timeDisposable: Disposable? = null
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
//                    BleConstant.DISCONNECTED -> {dispose()}
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
            CoroutineScope(Dispatchers.IO).launch {
                timeString.split("").forEach {
                    val timeByteArray = it.toByteArray()
                    if (timeByteArray.isNotEmpty()) {
                        timeDisposable = bluetooth.connectionFlow.value
                            .flatMapSingle { rxBleConnection ->
                                rxBleConnection.writeCharacteristic(BluetoothRX.writeTimeUUID, timeByteArray)
                            }
                            .subscribe(
                                { data ->
                                    appLog("Writing time: $data")
                                },
                                { error ->
                                    appLog("Writing time error: $error")
                                }
                            )
                    }
                    delay(500)
                }
            }
        }
    }

    override suspend fun writePeaks(peaks: Int) {
        val byteValue = ByteBuffer.allocate(4).putInt(peaks).array()
        byteValue.reverse()
        phaseDisposable = bluetooth.connectionFlow.value
            .flatMapSingle { rxBleConnection ->
                rxBleConnection.writeCharacteristic(BluetoothRX.writePeaksUUID, byteValue)
            }
            .subscribe(
                {
                    appLog( "Writing peaks: $it")
                },
                {
                    appLog( "Writing peaks error: $it")
                }
            )

    }

    override suspend fun writeTonic(value: Int) {
        val byteValue = ByteBuffer.allocate(4).putInt(value).array()
        byteValue.reverse()
        tonicDisposable = bluetooth.connectionFlow.value
            .flatMapSingle { rxBleConnection ->
                rxBleConnection.writeCharacteristic(BluetoothRX.writeTonicUUID, byteValue)
            }
            .subscribe(
                {
                    appLog("Writing tonic: $it")
                },
                {
                    appLog("Writing tonic error: $it")
                }
            )
    }
}