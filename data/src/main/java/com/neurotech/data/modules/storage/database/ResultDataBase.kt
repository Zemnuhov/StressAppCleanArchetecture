package com.neurotech.test.storage.database

import android.content.Context
import com.neurotech.test.App
import com.neurotech.test.storage.ResultStorage
import com.neurotech.test.storage.database.dao.ResultDao
import com.neurotech.test.storage.database.entity.ResultEntity
import com.neurotech.test.storage.database.entity.ResultSourceCounterItem
import com.neurotech.test.storage.database.entity.ResultTimeAndPeak
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResultDataBase(context: Context):ResultStorage {

    @Inject
    lateinit var dao: ResultDao

    init {
        (context as App).component.inject(this)
    }

    override suspend fun getTenMinuteCount(): Int {
        return dao.getTenMinuteCount()
    }

    override suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeak>> {
        return dao.getResultsInOneHour()
    }

    override suspend fun getCountBySources(sources: List<String>): Flow<List<ResultSourceCounterItem>> {
        return dao.getCountBySources(sources)
    }

    override suspend fun saveResult(resultEntity: ResultEntity) {
        dao.insertResult(resultEntity)
    }
}