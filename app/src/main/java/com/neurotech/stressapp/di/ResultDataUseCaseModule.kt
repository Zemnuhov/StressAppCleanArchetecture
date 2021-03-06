package com.neurotech.stressapp.di

import com.neurotech.domain.repository.ResultDataRepository
import com.neurotech.domain.usecases.resultdata.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ResultDataUseCaseModule {

    @Provides
    @Singleton
    fun providesGetCountBySources(repository: ResultDataRepository): GetCountBySources {
        return GetCountBySources(repository)
    }

    @Provides
    @Singleton
    fun providesGetResultsInOneHour(repository: ResultDataRepository): GetResultsInOneHour {
        return GetResultsInOneHour(repository)
    }

    @Provides
    @Singleton
    fun providesWriteResult(repository: ResultDataRepository): WriteResult {
        return WriteResult(repository)
    }


    @Provides
    @Singleton
    fun providesGetTenMinuteResultCount(repository: ResultDataRepository): GetTenMinuteResultCount {
        return GetTenMinuteResultCount(repository)
    }

    @Provides
    @Singleton
    fun providesSetStressCauseByTime(repository: ResultDataRepository): SetStressCauseByTime {
        return SetStressCauseByTime(repository)
    }

    @Provides
    @Singleton
    fun providesGetGoingBeyondLimit(repository: ResultDataRepository): GetGoingBeyondLimit {
        return GetGoingBeyondLimit(repository)
    }

    @Provides
    @Singleton
    fun providesGetResults(repository: ResultDataRepository): GetResults {
        return GetResults(repository)
    }

}