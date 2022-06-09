package com.neurotech.stressapp.di

import android.content.Context
import com.neurotech.stressapp.service.Service
import com.neurotech.stressapp.service.StressAppService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideService(context: Context): Service {
        return StressAppService(context)
    }


}