package com.neurotech.data.di

import android.content.Context
import androidx.room.Room
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.data.modules.storage.database.dao.MarkupDao

import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.test.storage.database.dao.TonicDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "stress_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMarkupDAO(database: AppDatabase): MarkupDao {
        return database.markupDao()
    }

    @Provides
    @Singleton
    fun providePeakDAO(database: AppDatabase): PeakDao {
        return database.peakDao()
    }

    @Provides
    @Singleton
    fun provideResultDAO(database: AppDatabase): ResultDao {
        return database.resultDao()
    }

    @Provides
    @Singleton
    fun provideTonicDAO(database: AppDatabase): TonicDao {
        return database.tonicDao()
    }

}