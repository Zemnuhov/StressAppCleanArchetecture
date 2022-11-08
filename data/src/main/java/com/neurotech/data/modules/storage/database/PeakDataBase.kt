package com.neurotech.data.modules.storage.database


import android.util.Log
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.PeakStorage
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.test.storage.database.entity.PeakEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeakDataBase(): PeakStorage {

    @Inject
    lateinit var dao: PeakDao

    init {
        component.inject(this)
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
        try {
            dao.insertPeak(peak)
        }catch (e: Exception){
            Log.e("PeakInsertError", e.toString())
        }

    }

    override suspend fun deletePeak() {
        dao.deletePeak()
    }
}