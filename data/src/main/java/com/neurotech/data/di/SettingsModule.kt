package com.neurotech.data.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neurotech.data.modules.settings.Settings
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.data.modules.settings.SharedPrefSettings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideSharedPrefSettings(context: Context):SharedPrefSettings{
        return SharedPrefSettings(context)
    }

    @Provides
    @Singleton
    fun provideSettings():Settings{
        return SettingsApi()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}