package com.neurotech.test.di

import android.content.Context
import com.neurotech.test.bluetooth.Bluetooth
import com.neurotech.test.bluetooth.connection.BluetoothConnection
import com.neurotech.test.bluetooth.connection.Connection
import com.neurotech.test.bluetooth.data.GsrData
import com.neurotech.test.bluetooth.data.GsrDataBluetooth
import com.neurotech.test.bluetooth.writing.WritingData
import com.neurotech.test.bluetooth.writing.WritingDataDevice
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetooth(context: Context):Bluetooth{
        return Bluetooth(context)
    }

    @Provides
    fun provideConnection(context: Context): Connection{
        return BluetoothConnection(context)
    }

    @Provides
    fun provideGsrData(context: Context): GsrData{
        return GsrDataBluetooth(context)
    }

    @Provides
    fun provideWriter(context: Context): WritingData{
        return WritingDataDevice(context)
    }

}