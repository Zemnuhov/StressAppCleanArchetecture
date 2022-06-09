package com.neurotech.data.di

import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnectionRX
import com.neurotech.data.modules.bluetooth.data.GsrDataBluetooth
import com.neurotech.data.modules.bluetooth.data.GsrDataBluetoothRx
import com.neurotech.data.modules.bluetooth.writing.WriteDataDeviceRx
import com.neurotech.data.modules.bluetooth.writing.WritingDataDevice
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
    fun inject(bluetooth: BluetoothConnection)
    fun inject(bluetooth: GsrDataBluetooth)
    fun inject(bluetooth: WritingDataDevice)
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
    fun inject(bluetoothConnectionRX: BluetoothConnectionRX)
    fun inject(gsrDataBluetoothRx: GsrDataBluetoothRx)
    fun inject(recodingInDeviceImpl: RecodingInDeviceImpl)
    fun inject(writeDataDeviceRx: WriteDataDeviceRx)

}