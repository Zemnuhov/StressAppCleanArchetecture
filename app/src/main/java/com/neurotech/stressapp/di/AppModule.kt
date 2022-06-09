package com.neurotech.stressapp.di

import android.content.Context
import com.neurotech.stressapp.notification.NotificationReceiver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext():Context{
        return context
    }

}