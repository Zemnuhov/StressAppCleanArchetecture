package com.neurotech.stressapp.di

import com.neurotech.domain.repository.GsrDataRepository
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.gsrdata.GetPhaseValuesInMemory
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.gsrdata.GetTonicValuesInMemory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsrDataUseCaseModule {
    @Provides
    @Singleton
    fun provideGetPhaseValueFlow(repository: GsrDataRepository): GetPhaseValueFlow {
        return GetPhaseValueFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetTonicValueFlow(repository: GsrDataRepository): GetTonicValueFlow {
        return GetTonicValueFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetTonicValueInMemory(repository: GsrDataRepository): GetTonicValuesInMemory {
        return GetTonicValuesInMemory(repository)
    }

    @Provides
    @Singleton
    fun provideGetPhaseValueInMemory(repository: GsrDataRepository): GetPhaseValuesInMemory {
        return GetPhaseValuesInMemory(repository)
    }

}