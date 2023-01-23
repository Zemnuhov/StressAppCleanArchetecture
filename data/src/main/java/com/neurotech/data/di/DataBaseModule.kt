package com.neurotech.data.di

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Room
import androidx.room.migration.Migration
import com.neurotech.data.modules.storage.database.DataBaseController
import com.neurotech.data.modules.storage.database.dao.*
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

    val MIGRATION_2_3 = Migration(2,3){
        it.execSQL("CREATE TABLE 'HourResultsEntity' (\n" +
                "    'date' TEXT NOT NULL, " +
                "    'tonic' INTEGER NOT NULL," +
                "    'peaks' INTEGER NOT NULL," +
                "    'peaksAvg' INTEGER NOT NULL," +
                "    'stressCause' TEXT," +
                "PRIMARY KEY ('date')"+
                ")")
    }

    val MIGRATION_3_4 = Migration(3,4){
        it.execSQL("CREATE TABLE 'DayResultEntity' (\n" +
                "    'date' TEXT NOT NULL, " +
                "    'tonic' INTEGER NOT NULL," +
                "    'peaks' INTEGER NOT NULL," +
                "    'peaksAvg' INTEGER NOT NULL," +
                "    'stressCause' TEXT," +
                "PRIMARY KEY ('date')"+
                ")")
    }

    val MIGRATION_4_5 = Migration(4,5){
        it.execSQL("CREATE TABLE 'UserEntity' (\n" +
                "    'id' INTEGER NOT NULL, " +
                "    'name' TEXT NOT NULL," +
                "    'age' INTEGER NOT NULL," +
                "    'tonicAvg' INTEGER NOT NULL," +
                "    'peakInDayNormal' INTEGER NOT NULL," +
                "    'peakInHourNormal' INTEGER NOT NULL," +
                "    'peakNormal' INTEGER NOT NULL," +
                "PRIMARY KEY ('id')"+
                ")")
    }

    val MIGRATION_5_6 = Migration(5,6){
        it.execSQL("ALTER TABLE UserEntity RENAME TO 'UserEntity_OLD'")
        it.execSQL("CREATE TABLE 'UserEntity' (\n" +
                "    'id' TEXT NOT NULL, " +
                "    'name' TEXT NOT NULL," +
                "    'age' INTEGER NOT NULL," +
                "    'tonicAvg' INTEGER NOT NULL," +
                "    'peakInDayNormal' INTEGER NOT NULL," +
                "    'peakInHourNormal' INTEGER NOT NULL," +
                "    'peakNormal' INTEGER NOT NULL," +
                "PRIMARY KEY ('id')"+
                ")")
    }

    val MIGRATION_6_7 = Migration(6,7){
        it.execSQL("ALTER TABLE UserEntity RENAME TO 'UserEntity_OLD1'")
        it.execSQL("CREATE TABLE 'UserEntity' (\n" +
                "    'id' TEXT NOT NULL, " +
                "    'name' TEXT NOT NULL," +
                "    'dateOfBirth' TEXT," +
                "    'gender' TEXT," +
                "    'tonicAvg' INTEGER NOT NULL," +
                "    'peakInDayNormal' INTEGER NOT NULL," +
                "    'peakInHourNormal' INTEGER NOT NULL," +
                "    'peakNormal' INTEGER NOT NULL," +
                "PRIMARY KEY ('id')"+
                ")")
    }

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "stress_database"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5,MIGRATION_5_6,MIGRATION_6_7)
            .build()
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

    @Provides
    @Singleton
    fun provideHourResultDAO(database: AppDatabase): HourResultDao {
        return database.hourResultDao()
    }

    @Provides
    @Singleton
    fun provideDayResultDAO(database: AppDatabase): DayResultDao {
        return database.dayResultDao()
    }

    @Provides
    @Singleton
    fun provideUserDAO(database: AppDatabase): UserDao {
        return database.userDao()
    }

//    @Provides
//    @Singleton
//    fun provideDataBaseController(context: Context): DataBaseController {
//        return DataBaseController(context)
//    }
}