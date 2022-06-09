package com.neurotech.stressapp.di

import com.neurotech.domain.repository.RecodingInDevice
import com.neurotech.domain.usecases.recorddevice.RecordPeaksInDevice
import com.neurotech.domain.usecases.recorddevice.RecordTimeInDevice
import com.neurotech.domain.usecases.recorddevice.RecordTonicInDevice
import com.neurotech.stressapp.service.DeviceBleWriter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecodingInDeviceUseCaseModule {

    @Provides
    @Singleton
    fun provideRecodingPeaks(repository: RecodingInDevice):RecordPeaksInDevice{
        return RecordPeaksInDevice(repository)
    }

    @Provides
    @Singleton
    fun provideRecodingTime(repository: RecodingInDevice):RecordTimeInDevice{
        return RecordTimeInDevice(repository)
    }

    @Provides
    @Singleton
    fun provideRecodingTonic(repository: RecodingInDevice):RecordTonicInDevice{
        return RecordTonicInDevice(repository)
    }

    @Provides
    @Singleton
    fun provideDeviceBleWriter(
        recodingPeaks: RecordPeaksInDevice,
        recordTonicInDevice: RecordTonicInDevice,
        recordTimeInDevice: RecordTimeInDevice
    ): DeviceBleWriter {
        return DeviceBleWriter(recodingPeaks,recordTonicInDevice, recordTimeInDevice)
    }

}