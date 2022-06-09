package com.neurotech.data.di

import android.content.Context
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.neurotech.data.modules.bluetooth.BluetoothRX
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnectionRX
import com.neurotech.data.modules.bluetooth.connection.Connection
import com.neurotech.data.modules.bluetooth.data.GsrData
import com.neurotech.data.modules.bluetooth.data.GsrDataBluetooth
import com.neurotech.data.modules.bluetooth.data.GsrDataBluetoothRx
import com.neurotech.data.modules.bluetooth.writing.WriteDataDeviceRx
import com.neurotech.data.modules.bluetooth.writing.WritingData
import com.neurotech.data.modules.bluetooth.writing.WritingDataDevice
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetoothRx(context: Context): BluetoothRX {
        return BluetoothRX(context)
    }

    @Provides
    @Singleton
    fun provideBluetooth(context: Context): Bluetooth {
        return Bluetooth(context)
    }


    @Provides
    @Singleton
    fun provideConnection(): Connection {
        return BluetoothConnectionRX()
    }

    @Provides
    @Singleton
    fun provideGsrData(): GsrData {
        return GsrDataBluetoothRx()
    }

    @Provides
    @Singleton
    fun provideWriter(): WritingData {
        return WriteDataDeviceRx()
    }

}