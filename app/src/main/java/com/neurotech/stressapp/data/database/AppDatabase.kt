package com.neurotech.stressapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neurotech.stressapp.data.database.dao.PeakDao
import com.neurotech.stressapp.data.database.dao.TonicDao
import com.neurotech.stressapp.data.database.entity.PeakEntity
import com.neurotech.stressapp.data.database.entity.TonicEntity

@Database(entities = [PeakEntity::class, TonicEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun peakDao(): PeakDao
    abstract fun tonicDao(): TonicDao
}