package com.neurotech.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.neurotech.data.modules.storage.database.dao.MarkupDao
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.test.storage.database.dao.TonicDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    val MIGRATION_1_2 = Migration(1,2){
        it.execSQL("ALTER TABLE ResultEntity ADD COLUMN keep VARCHAR(255) DEFAULT null")
    }

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "stress_database"
        ).addMigrations(MIGRATION_1_2).build()
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