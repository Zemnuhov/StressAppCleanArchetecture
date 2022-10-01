package com.neurotech.data.modules.bluetooth.data.dataflow

import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.neurotech.data.modules.bluetooth.Bluetooth.Companion.phaseFlowUUID
import com.neurotech.data.modules.bluetooth.Bluetooth.Companion.tonicFlowUUID
import com.neurotech.data.modules.bluetooth.data.GsrData
import com.neurotech.data.modules.bluetooth.data.filters.ExpRunningAverage
import com.neurotech.domain.BleConstant
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GsrDataBluetooth: GsrData {

    @Inject
    lateinit var bluetooth: Bluetooth

    private val tonicValueFlow = MutableStateFlow(TonicModelBluetooth(0, Date()))
    private val phaseValueFlow = MutableStateFlow(PhaseModelBluetooth(0.0, Date()))

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
        bluetooth.connectionFlow.collect{
            if(it != null){
                tonicDisposable?.dispose()
                tonicDisposable =
                    it.flatMap { rxBleConnection ->
                        rxBleConnection.setupNotification(
                            tonicFlowUUID
                        )
                    }
                        .flatMap { v -> v }
                        .map { v -> ByteBuffer.wrap(v).int }
                        .concatMap { v ->
                            Observable.just(v).delay(25, TimeUnit.MILLISECONDS)
                        }
                        .subscribe(
                            { v ->
                                if(v in 0..10000){
                                    tonicValueFlow.value = TonicModelBluetooth(v, Date())
                                }
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
        val filter = ExpRunningAverage(0.1)
        bluetooth.connectionFlow.collect{
            if(it != null){
                phaseDisposable?.dispose()
                phaseDisposable =
                    it.flatMap { rxBleConnection ->
                        rxBleConnection.setupNotification(
                            phaseFlowUUID
                        )
                    }
                        .flatMap { v -> v }
                        .map { v -> ByteBuffer.wrap(v).int }
                        .map { v -> filter.filter(v) }
                        .concatMap { v ->
                            Observable.just(v).delay(25, TimeUnit.MILLISECONDS)
                        }
                        .subscribe(
                            { phaseValue ->
                                phaseValueFlow.value = PhaseModelBluetooth(phaseValue.toDouble(), Date())
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