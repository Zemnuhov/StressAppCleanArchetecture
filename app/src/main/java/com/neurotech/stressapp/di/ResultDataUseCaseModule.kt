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
    fun providesSetKeepByTime(repository: ResultDataRepository): SetKeepByTime {
        return SetKeepByTime(repository)
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

    @Provides
    @Singleton
    fun providesGetResultsInMonth(repository: ResultDataRepository): GetResultsInMonth {
        return GetResultsInMonth(repository)
    }

    @Provides
    @Singleton
    fun providesGetResultsCountAndSourceInInterval(repository: ResultDataRepository): GetResultsCountAndSourceInInterval {
        return GetResultsCountAndSourceInInterval(repository)
    }

    @Provides
    @Singleton
    fun providesGetResultsInInterval(repository: ResultDataRepository): GetResultsByInterval {
        return GetResultsByInterval(repository)
    }
}