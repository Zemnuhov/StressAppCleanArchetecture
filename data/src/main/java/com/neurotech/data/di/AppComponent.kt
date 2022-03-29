package com.neurotech.test.di

import com.neurotech.data.di.AppModule
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection

import com.neurotech.test.bluetooth.data.GsrDataBluetooth
import com.neurotech.test.bluetooth.writing.WritingDataDevice
import com.neurotech.test.service.AppService
import com.neurotech.test.service.DataFlowAnalyzer
import com.neurotech.test.settings.SettingsApi
import com.neurotech.test.storage.database.MarkupDataBase
import com.neurotech.test.storage.database.PeakDataBase
import com.neurotech.test.storage.database.ResultDataBase
import com.neurotech.test.storage.database.TonicDataBase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BluetoothModule::class, AppModule::class])
interface AppComponent {
    fun inject(bluetooth: GsrDataBluetooth)
    fun inject(bluetooth: BluetoothConnection)

    fun inject(writingDataDevice: WritingDataDevice)
    fun inject(dataBase: TonicDataBase)
    fun inject(dataBase: ResultDataBase)
    fun inject(dataBase: PeakDataBase)
    fun inject(dataBase: MarkupDataBase)
    fun inject(settings: SettingsApi)
    fun inject(service: AppService)
    fun inject(dataFlowAnalyzer: DataFlowAnalyzer)
}