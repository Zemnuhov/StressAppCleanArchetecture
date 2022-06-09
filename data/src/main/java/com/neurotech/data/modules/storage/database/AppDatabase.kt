package com.neurotech.stressapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neurotech.data.modules.storage.database.dao.MarkupDao
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.data.modules.storage.database.entity.MarkupEntity
import com.neurotech.test.storage.database.entity.PeakEntity
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.test.storage.database.entity.TonicEntity

@Database(entities = [PeakEntity::class, TonicEntity::class, ResultEntity::class, MarkupEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun peakDao(): PeakDao
    abstract fun tonicDao(): TonicDao
    abstract fun resultDao(): ResultDao
    abstract fun markupDao(): MarkupDao
}