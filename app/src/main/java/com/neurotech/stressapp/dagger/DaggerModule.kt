package com.neurotech.stressapp.dagger

import com.neurotech.stressapp.Singleton.context
import com.neurotech.stressapp.data.ConnectionRepositoryImpl
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import javax.inject.Singleton


@Module
class DaggerModule{


    @Provides
    @Singleton
    fun bleClient(): RxBleClient {
        return RxBleClient.create(context)
    }

    @Provides
    @Singleton
    fun bleDevice(): RxBleDevice{
        return bleClient().getBleDevice(SettingsApi().getDevice()!!)
    }


    @Provides
    @Singleton
    fun getBleConnection(): Observable<RxBleConnection> {
        return bleDevice()
            .establishConnection(true)
            .replay()
            .autoConnect()
    }

    @Provides
    @Singleton
    fun getConnectionRepository(): ConnectionRepository{
        return ConnectionRepositoryImpl()
    }
}

