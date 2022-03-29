package com.neurotech.stressapp.di

import com.neurotech.stressapp.data.implementations.*
import com.neurotech.stressapp.domain.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaggerRepositoryModule {

    @Provides
    @Singleton
    fun provideConnectionRepository(): ConnectionRepository {
        return ConnectionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMainFunctionsRepository(): MainFunctions {
        return MainFunctionsImpl()
    }

    @Provides
    @Singleton
    fun provideGraphRepository(): GraphRepository {
        return GraphRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideTonicCaseRepository(): TonicCaseRepository {
        return TonicCaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePhaseCaseRepository(): PhaseCaseRepository {
        return PhaseCaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideStatisticCaseRepository(): StatisticCaseRepository {
        return StatisticCaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSourceCaseRepository(): SourceCaseRepository{
        return SourceCaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMarkupCaseRepository(): MarkupCaseRepository{
        return MarkupCaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMarkupSettingsRepository(): MarkupSettingsRepository{
        return MarkupSettingsRepositoryImpl()
    }
}