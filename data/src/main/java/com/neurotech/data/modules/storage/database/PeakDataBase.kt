package com.neurotech.test.storage.database

import android.content.Context
import com.neurotech.test.App
import com.neurotech.test.storage.PeakStorage
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.test.storage.database.entity.PeakEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeakDataBase(context: Context):PeakStorage {

    @Inject
    lateinit var dao: PeakDao

    init {
        (context as App).component.inject(this)
    }

    override suspend fun getTenMinuteCountFlow(): Flow<Int> {
        return dao.getTenMinuteCountFlow()
    }

    override suspend fun getTenMinuteCount(): Int {
        return dao.getTenMinuteCount()
    }

    override suspend fun getOneHourCountFlow(): Flow<Int> {
        return dao.getOneHourCountFlow()
    }

    override suspend fun getOneDayCountFlow(): Flow<Int> {
        return dao.getOneDayCountFlow()
    }

    override suspend fun savePeak(peak: PeakEntity) {
        dao.insertPeak(peak)
    }

    override suspend fun deletePeak() {
        dao.deletePeak()
    }
}