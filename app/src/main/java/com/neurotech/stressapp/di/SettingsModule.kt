package com.neurotech.stressapp.di

import com.neurotech.domain.repository.SettingsRepository
import com.neurotech.domain.usecases.settings.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideAddStimulus(repository: SettingsRepository): AddStimulus{
        return AddStimulus(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteStimulus(repository: SettingsRepository): DeleteStimulus{
        return DeleteStimulus(repository)
    }

    @Provides
    @Singleton
    fun provideGetStimulusList(repository: SettingsRepository): GetStimulusList{
        return GetStimulusList(repository)
    }

    @Provides
    @Singleton
    fun provideGetThreshold(repository: SettingsRepository): GetThreshold{
        return GetThreshold(repository)
    }

    @Provides
    @Singleton
    fun provideGetDeviceInMemory(repository: SettingsRepository): GetDeviceInMemory{
        return GetDeviceInMemory(repository)
    }
}