package com.neurotech.stressapp.data

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.Singleton.context
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.concurrent.thread

class ConnectionRepositoryImpl: ConnectionRepository {
    @Inject
    lateinit var bleClient: RxBleClient
    @Inject
    lateinit var device: RxBleDevice
    @Inject
    lateinit var connection: Observable<RxBleConnection>
    private var deviceSet = hashSetOf<Device>()
    private var deviceSetLiveData = MutableLiveData<List<Device>>()
    lateinit var bleService: BleService
    lateinit var searchDisposable: Disposable
    private var searchState = MutableLiveData<Boolean>()
    private var connectionState = MutableLiveData<RxBleConnection.RxBleConnectionState>()

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BleService.LocalBinder
            bleService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }

    init {
        Singleton.daggerComponent.inject(this)
        searchState.value = false
    }

    override fun getDeviceState(): LiveData<RxBleConnection.RxBleConnectionState> {
        val disposable = device.observeConnectionStateChanges()
            .subscribeOn(Schedulers.computation())
            .subscribe{
                connectionState.value = it
            }
        return connectionState
    }

    override fun getListDevice(): LiveData<List<Device>> {
        searchState.postValue(true)
        val rxBleClient: RxBleClient = RxBleClient.create(context)
        searchDisposable = rxBleClient
            .scanBleDevices(ScanSettings.Builder().build())
            .subscribeOn(Schedulers.io())
            .subscribe {
                deviceSet.add(Device(it.bleDevice.name ?: "No name", it.bleDevice.macAddress))
                deviceSetLiveData.value = deviceSet.toList()
            }

        thread {
            Thread.sleep(10000)
            searchDisposable.dispose()
            searchState.postValue(false)
        }
        return deviceSetLiveData
    }

    override fun connectionToDevice(MAC: String) {
        searchDisposable.dispose()
        SettingsApi().saveDevice(MAC)
        device = updateBleDevice()
        connection = updateBleConnection()
        val intent = Intent(context, BleService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    override fun searchState(): LiveData<Boolean> {
        return searchState
    }

    private fun updateBleDevice():RxBleDevice{
        return bleClient.getBleDevice(SettingsApi().getDevice()!!)
    }
    private fun updateBleConnection(): Observable<RxBleConnection> {
        return device
            .establishConnection(true)
            .replay()
            .autoConnect()
    }




}