package com.neurotech.data.modules.bluetooth.data

import android.util.Log
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.BluetoothRX
import com.neurotech.data.modules.bluetooth.BluetoothRX.Companion.notificationDataUUID
import com.neurotech.data.modules.bluetooth.data.filters.ExpRunningAverage
import com.neurotech.data.modules.bluetooth.data.filters.KalmanFilter
import com.neurotech.data.modules.bluetooth.data.filters.ValueConverter
import com.neurotech.domain.BleConstant
import com.neurotech.domain.Codes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class GsrDataBluetoothRx: GsrData {

    @Inject
    lateinit var bluetooth: BluetoothRX

    private val tonicValueFlow = MutableStateFlow(TonicModelBluetooth(0, Date()))
    private val phaseValueFlow = MutableStateFlow(PhaseModelBluetooth(0.0, Date()))
    private val valueConverter = ValueConverter()

    private var phaseDisposable:Disposable? = null
    private var tonicDisposable:Disposable? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        component.inject(this)
        scope.launch {
            bluetooth.getConnectionFlow().collect{
                when(it){
                    BleConstant.CONNECTED -> {
                        dispose()
                        beginPhaseFlow()
                        beginTonicFlow()
                    }
                }
            }
        }

    }

    private fun dispose(){
        phaseDisposable?.dispose()
        tonicDisposable?.dispose()
    }

    private suspend fun beginTonicFlow(){
        val kalmanFilter = KalmanFilter(0.0, 0.1)
        val expRunningAverage = ExpRunningAverage(0.01)
        bluetooth.connectionFlow.collect{
            if(it != null){
                tonicDisposable?.dispose()
                tonicDisposable =
                    it.flatMap { rxBleConnection ->
                        rxBleConnection.setupNotification(
                            notificationDataUUID
                        )
                    }
                        .flatMap { v -> v }
                        .map { v -> ByteBuffer.wrap(v).int }
                        .map { v -> valueConverter.rangeConvert(v) }
                        .map { v -> kalmanFilter.correct(v) }
                        .map { v -> expRunningAverage.filter(v).toInt() }
                        .subscribe(
                            { v ->
                                tonicValueFlow.value = TonicModelBluetooth(v, Date())
                            },
                            { v ->
                                appLog("Ble tonic data flow error: $v")
                            }
                        )

            }
        }
    }

    override suspend fun getTonicValueFlow(): Flow<TonicModelBluetooth> {
        if (tonicDisposable == null){
            beginTonicFlow()
        }
        return tonicValueFlow
    }

    private suspend fun beginPhaseFlow(){
        val kalmanFilter = KalmanFilter(0.0, 0.1)
        val expRunningAverage = ExpRunningAverage(0.01)
        val expRunningAverageTonic = ExpRunningAverage(0.1)
        bluetooth.connectionFlow.collect{
            if(it != null){
                phaseDisposable?.dispose()
                phaseDisposable =
                    it.flatMap { rxBleConnection ->
                        rxBleConnection.setupNotification(
                            notificationDataUUID
                        )
                    }
                        .flatMap { v -> v }
                        .map { v -> ByteBuffer.wrap(v).int }
                        .map { v -> valueConverter.rangeConvert(v) }
                        .map { v -> expRunningAverageTonic.filter(v).toInt() }
                        .map { v -> valueConverter.toPhaseValue(v) }
                        .map { v -> kalmanFilter.correct(v) }
                        .map { v -> expRunningAverage.filter(v) }
                        .subscribe(
                            { v ->
                                phaseValueFlow.value = PhaseModelBluetooth(v, Date())
                            },
                            { v ->
                                appLog("Ble phase data flow error: $v")
                            }
                        )

            }
        }
    }

    override suspend fun getPhaseValueFlow(): Flow<PhaseModelBluetooth> {
        if(phaseDisposable == null) {
            beginPhaseFlow()
        }
        return phaseValueFlow
    }
}