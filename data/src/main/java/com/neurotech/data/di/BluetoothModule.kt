package com.neurotech.data.di

import android.content.Context
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import com.neurotech.data.modules.bluetooth.bluetoothscan.BleScanner
import com.neurotech.data.modules.bluetooth.bluetoothscan.DeviceScanner
import com.neurotech.data.modules.bluetooth.connection.BleConnection
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.data.modules.bluetooth.data.DataFlow
import com.neurotech.data.modules.bluetooth.data.GsrData
import com.neurotech.data.modules.bluetooth.syncdata.SyncData
import com.neurotech.data.modules.bluetooth.syncdata.SyncDataFromDevice
import com.neurotech.data.modules.bluetooth.writing.WritingData
import com.neurotech.data.modules.bluetooth.writing.WritingDataToDevice

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetoothManager(context: Context): AppBluetoothManager {
        return AppBluetoothManager(context)
    }

    @Provides
    @Singleton
    fun provideBluetoothScanner(): DeviceScanner {
        return BleScanner()
    }

    @Provides
    @Singleton
    fun provideConnection(): BluetoothConnection {
        return BleConnection()
    }

    @Provides
    @Singleton
    fun provideGsrData(): GsrData {
        return DataFlow()
    }

    @Provides
    @Singleton
    fun provideWriter(): WritingData {
        return WritingDataToDevice()
    }

    @Provides
    @Singleton
    fun provideSyncData(): SyncData {
        return SyncDataFromDevice()
    }


}