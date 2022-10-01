package com.neurotech.data.di

import android.content.Context
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.data.modules.bluetooth.connection.Connection
import com.neurotech.data.modules.bluetooth.data.GsrData
import com.neurotech.data.modules.bluetooth.data.dataflow.GsrDataBluetooth
import com.neurotech.data.modules.bluetooth.syncdata.SyncBlePeaks
import com.neurotech.data.modules.bluetooth.syncdata.SyncData
import com.neurotech.data.modules.bluetooth.writing.WriteDataDevice
import com.neurotech.data.modules.bluetooth.writing.WritingData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetooth(context: Context): Bluetooth {
        return Bluetooth(context)
    }

    @Provides
    @Singleton
    fun provideConnection(): Connection {
        return BluetoothConnection()
    }

    @Provides
    @Singleton
    fun provideGsrData(): GsrData {
        return GsrDataBluetooth()
    }

    @Provides
    @Singleton
    fun provideWriter(): WritingData {
        return WriteDataDevice()
    }

    @Provides
    @Singleton
    fun provideSyncData(): SyncData {
        return SyncBlePeaks()
    }


}