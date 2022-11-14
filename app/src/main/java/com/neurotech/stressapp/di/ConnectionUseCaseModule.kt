package com.neurotech.stressapp.di

import com.neurotech.domain.repository.ConnectionRepository
import com.neurotech.domain.repository.SettingsRepository
import com.neurotech.domain.usecases.connection.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConnectionUseCaseModule {

    @Provides
    @Singleton
    fun provideConnectionToPeripheral(
        connectionRepository: ConnectionRepository,
        settingsRepository: SettingsRepository
    ): ConnectionToPeripheral{
        return ConnectionToPeripheral(connectionRepository,settingsRepository)
    }

    @Provides
    @Singleton
    fun provideDisconnectDevice(repository: ConnectionRepository): DisconnectDevice {
        return DisconnectDevice(repository)
    }

    @Provides
    @Singleton
    fun provideGetConnectionStateFlow(repository: ConnectionRepository): GetConnectionStateFlow{
        return GetConnectionStateFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetDeviceListFlow(repository: ConnectionRepository): GetDeviceListFlow{
        return GetDeviceListFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetScanState(repository: ConnectionRepository): GetScanState{
        return GetScanState(repository)
    }

    @Provides
    @Singleton
    fun provideStopScan(repository: ConnectionRepository): StopScan{
        return StopScan(repository)
    }

    @Provides
    @Singleton
    fun provideGetConnectionState(repository: ConnectionRepository): GetConnectionState{
        return GetConnectionState(repository)
    }
}