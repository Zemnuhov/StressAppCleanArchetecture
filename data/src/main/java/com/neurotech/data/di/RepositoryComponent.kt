package com.neurotech.data.di

import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.data.modules.bluetooth.data.dataflow.GsrDataBluetooth
import com.neurotech.data.modules.bluetooth.syncdata.SyncBlePeaks
import com.neurotech.data.modules.bluetooth.writing.WriteDataDevice
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.data.modules.storage.database.MarkupDataBase
import com.neurotech.data.modules.storage.database.PeakDataBase
import com.neurotech.data.modules.storage.database.ResultDataBase
import com.neurotech.data.modules.storage.database.TonicDataBase
import com.neurotech.data.repository.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        BluetoothModule::class,
        AppModule::class,
        DataBaseModule::class,
        SettingsModule::class,
        StorageModule::class,
    ]
)
interface RepositoryComponent {
    fun inject(settingsApi: SettingsApi)
    fun inject(dataBase: MarkupDataBase)
    fun inject(peakDataBase: PeakDataBase)
    fun inject(dataBase: ResultDataBase)
    fun inject(dataBase: TonicDataBase)
    fun inject(repository: TonicRepositoryImpl)
    fun inject(repository: SettingsRepositoryImpl)
    fun inject(repository: PhaseRepositoryImpl)
    fun inject(repository: MarkupRepositoryImpl)
    fun inject(repository: GsrDataRepositoryImpl)
    fun inject(repository: ConnectionRepositoryImpl)
    fun inject(repository: ResultDataRepositoryImpl)
    fun inject(bluetoothConnection: BluetoothConnection)
    fun inject(gsrDataBluetooth: GsrDataBluetooth)
    fun inject(recodingInDeviceImpl: RecodingInDeviceImpl)
    fun inject(writeDataDevice: WriteDataDevice)
    fun inject(syncBlePeaks: SyncBlePeaks)
}