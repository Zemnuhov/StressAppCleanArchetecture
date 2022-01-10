package com.neurotech.stressapp.dagger

import com.neurotech.stressapp.Singleton.context
import com.neurotech.stressapp.data.BleConnection
import com.neurotech.stressapp.data.ConnectionRepositoryImpl
import com.neurotech.stressapp.data.MainFunctionsImpl
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.neurotech.stressapp.domain.repository.MainFunctions
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
    fun getConnectionRepository(): ConnectionRepository{
        return ConnectionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun getMainFunctionsRepository(): MainFunctions{
        return MainFunctionsImpl()
    }

    @Provides
    @Singleton
    fun getBleConnection(): BleConnection{
        return BleConnection()
    }
}

