package com.neurotech.stressapp.dagger

import com.neurotech.stressapp.data.implementations.ConnectionRepositoryImpl
import com.neurotech.stressapp.data.implementations.GraphRepositoryImpl
import com.neurotech.stressapp.data.implementations.MainFunctionsImpl
import com.neurotech.stressapp.data.implementations.TonicCaseRepositoryImpl
import com.neurotech.stressapp.domain.repository.ConnectionRepository
import com.neurotech.stressapp.domain.repository.GraphRepository
import com.neurotech.stressapp.domain.repository.MainFunctions
import com.neurotech.stressapp.domain.repository.TonicCaseRepository
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
}