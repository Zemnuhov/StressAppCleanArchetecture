package com.neurotech.stressapp.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.neurotech.data.modules.storage.database.dao.*
import com.neurotech.data.modules.storage.database.entity.*
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.test.storage.database.entity.PeakEntity
import com.neurotech.test.storage.database.entity.TonicEntity

@Database(
    entities = [PeakEntity::class, TonicEntity::class, ResultEntity::class, MarkupEntity::class, HourResultsEntity::class, DayResultEntity::class, UserEntity::class],
    version = 7)
abstract class AppDatabase: RoomDatabase() {
    abstract fun peakDao(): PeakDao
    abstract fun tonicDao(): TonicDao
    abstract fun resultDao(): ResultDao
    abstract fun markupDao(): MarkupDao
    abstract fun hourResultDao(): HourResultDao
    abstract fun dayResultDao(): DayResultDao
    abstract fun userDao(): UserDao
}