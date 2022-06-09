package com.neurotech.stressapp.di

import com.neurotech.domain.repository.PhaseRepository
import com.neurotech.domain.usecases.phasedata.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PhaseDataUseCaseModule {

    @Provides
    @Singleton
    fun provideGetOneDayCountFlow(repository: PhaseRepository): GetOneDayCountFlow{
        return GetOneDayCountFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetOneHourCountFlow(repository: PhaseRepository): GetOneHourCountFlow{
        return GetOneHourCountFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetTenMinuteCountFlow(repository: PhaseRepository): GetTenMinuteCountFlow{
        return GetTenMinuteCountFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetTenMinuteCount(repository: PhaseRepository): GetTenMinuteCountPhase{
        return GetTenMinuteCountPhase(repository)
    }

    @Provides
    @Singleton
    fun provideWritePeak(repository: PhaseRepository): WritePhase{
        return WritePhase(repository)
    }

}