package com.neurotech.stressapp.di

import com.neurotech.domain.repository.GsrDataRepository
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
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

}