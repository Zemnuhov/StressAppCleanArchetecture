package com.neurotech.stressapp.data

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.SourceStressAndCountItem
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.nio.ByteBuffer
import javax.inject.Inject

class MainFunctionsImpl: MainFunctions {

    @Inject
    lateinit var device: RxBleDevice
    @Inject
    lateinit var connection: Observable<RxBleConnection>



    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun getAvgTonicValue(timeInterval: Long): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun getDeviceState(): LiveData<RxBleConnection.RxBleConnectionState> {
        return LiveDataReactiveStreams.fromPublisher(device.observeConnectionStateChanges().toFlowable(
            BackpressureStrategy.BUFFER))
    }


    override fun getNumberOfPeak(timeInterval: Long): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun getSourceStressAndCountItemList(): LiveData<List<SourceStressAndCountItem>> {
        TODO("Not yet implemented")
    }

    override fun getTonicValue(): LiveData<Int> {
        return LiveDataReactiveStreams.fromPublisher {
            connection
                .subscribeOn(Schedulers.computation())
                .flatMap { rxBleConnection -> rxBleConnection.setupNotification(BleService.notificationDataUUID) }
                .flatMap { it }
                .map { ByteBuffer.wrap(it).int }
                .subscribe()
        }
    }

    override fun getValueForGraphPhase(): LiveData<Float> {
        TODO("Not yet implemented")
    }

    override fun getValueForGraphTonic(): LiveData<Float> {
        TODO("Not yet implemented")
    }

    override fun increaseCountSourceStressItem(sourceStressAndCountItem: SourceStressAndCountItem) {
        TODO("Not yet implemented")
    }
}