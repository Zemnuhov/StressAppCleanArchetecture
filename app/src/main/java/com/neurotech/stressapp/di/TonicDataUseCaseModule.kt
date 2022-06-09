package com.neurotech.stressapp.di

import com.neurotech.domain.repository.TonicRepository
import com.neurotech.domain.usecases.tonicdata.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TonicDataUseCaseModule {

    @Provides
    @Singleton
    fun provideGetOneDayAvgFlow(repository: TonicRepository): GetOneDayAvgFlow{
        return GetOneDayAvgFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetOneHourAvgFlow(repository: TonicRepository): GetOneHourAvgFlow{
        return GetOneHourAvgFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetTenMinuteAvgFlow(repository: TonicRepository): GetTenMinuteAvgFlow{
        return GetTenMinuteAvgFlow(repository)
    }

    @Provides
    @Singleton
    fun provideGetTenMinuteAvg(repository: TonicRepository): GetTenMinuteAvgTonic{
        return GetTenMinuteAvgTonic(repository)
    }

    @Provides
    @Singleton
    fun provideWriteTonic(repository: TonicRepository): WriteTonic{
        return WriteTonic(repository)
    }

}