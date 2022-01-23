package com.neurotech.stressapp.dagger

import androidx.room.Room
import androidx.room.RoomDatabase
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.data.database.AppDatabase
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

