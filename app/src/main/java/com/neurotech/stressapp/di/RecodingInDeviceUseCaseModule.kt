package com.neurotech.stressapp.di

import com.neurotech.domain.repository.RecodingInDevice
import com.neurotech.domain.usecases.recorddevice.RecordNotifyFlag
import com.neurotech.domain.usecases.recorddevice.RecordTimeInDevice
import com.neurotech.stressapp.service.DeviceBleWriter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecodingInDeviceUseCaseModule {


    @Provides
    @Singleton
    fun provideRecordNotifyFlag(repository: RecodingInDevice):RecordNotifyFlag{
        return RecordNotifyFlag(repository)
    }

    @Provides
    @Singleton
    fun provideRecodingTime(repository: RecodingInDevice):RecordTimeInDevice{
        return RecordTimeInDevice(repository)
    }



    @Provides
    @Singleton
    fun provideDeviceBleWriter(

        recordTimeInDevice: RecordTimeInDevice
    ): DeviceBleWriter {
        return DeviceBleWriter(recordTimeInDevice)
    }

}