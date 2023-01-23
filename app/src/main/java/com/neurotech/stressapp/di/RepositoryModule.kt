package com.neurotech.stressapp.di

import com.neurotech.data.repository.*
import com.neurotech.domain.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideConnectionRepository(): ConnectionRepository{
        return ConnectionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGsrDataRepository(): GsrDataRepository {
        return GsrDataRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMarkupRepository(): MarkupRepository {
        return MarkupRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePhaseRepository(): PhaseRepository {
        return PhaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideResultDataRepository(): ResultDataRepository {
        return ResultDataRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideTonicRepository(): TonicRepository {
        return TonicRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRecodingInDevice(): RecodingInDevice {
        return RecodingInDeviceImpl()
    }

    @Provides
    @Singleton
    fun provideHourResultRepository(): HourResultRepository {
        return HourResultRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDayResultRepository(): DayResultRepository {
        return DayResultRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

}