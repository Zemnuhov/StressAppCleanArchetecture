package com.neurotech.data.di

import com.neurotech.data.modules.bluetooth.connection.BleConnection
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.data.modules.bluetooth.data.DataFlow
import com.neurotech.data.modules.bluetooth.syncdata.SyncDataFromDevice
import com.neurotech.data.modules.bluetooth.writing.WritingDataToDevice
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.data.modules.storage.database.DataBaseController
import com.neurotech.data.modules.storage.firebase.FirebaseData
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
    ]
)
interface RepositoryComponent {
    fun inject(settingsApi: SettingsApi)
    fun inject(repository: TonicRepositoryImpl)
    fun inject(repository: SettingsRepositoryImpl)
    fun inject(repository: PhaseRepositoryImpl)
    fun inject(repository: MarkupRepositoryImpl)
    fun inject(repository: GsrDataRepositoryImpl)
    fun inject(repository: ConnectionRepositoryImpl)
    fun inject(repository: ResultDataRepositoryImpl)
    fun inject(bluetoothConnection: BluetoothConnection)
    fun inject(recodingInDeviceImpl: RecodingInDeviceImpl)
    fun inject(bleConnection: BleConnection)
    fun inject(dataFlow: DataFlow)
    fun inject(syncDataFromDevice: SyncDataFromDevice)
    fun inject(writingDataToDevice: WritingDataToDevice)
    fun inject(hourResultRepositoryImpl: HourResultRepositoryImpl)
    fun inject(dataBaseController: DataBaseController)
    fun inject(dayResultRepositoryImpl: DayResultRepositoryImpl)
    fun inject(userRepositoryImpl: UserRepositoryImpl)
    fun inject(firebaseData: FirebaseData)
}