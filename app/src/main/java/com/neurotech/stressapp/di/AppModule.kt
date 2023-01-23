package com.neurotech.stressapp.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext():Context{
        return context
    }

    @Provides
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics{
        return Firebase.analytics
    }

    @Provides
    fun provideFirebaseAuth(context: Context): FirebaseAuth{
        return Firebase.auth
    }

}