package com.neurotech.stressapp.di

import androidx.room.Room
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DaggerModule{
    @Provides
    @Singleton
    fun provideBleConnection(): BleConnection {
        return BleConnection()
    }

    @Provides
    @Singleton
    fun provideDataBase(): AppDatabase {
        return Room.databaseBuilder(
            com.neurotech.stressapp.Singleton.context,
            AppDatabase::class.java, "stress_database"
        ).build()
    }
}

