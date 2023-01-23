package com.neurotech.data.di

import android.content.Context
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.neurotech.data.modules.storage.firebase.FirebaseAPI
import com.neurotech.data.modules.storage.firebase.FirebaseData
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import javax.inject.Singleton


@Module
class AppModule(private val context:Context) {
    @Provides
    fun provideContext():Context{
        return context
    }

    @Provides
    fun provideFirebaseDataBase(): FirebaseDatabase{
        return Firebase.database
    }

    @Provides
    @Singleton
    fun provideFirebaseData(): FirebaseAPI{
        return FirebaseData()
    }

    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager{
        return WorkManager.getInstance(context)
    }
}