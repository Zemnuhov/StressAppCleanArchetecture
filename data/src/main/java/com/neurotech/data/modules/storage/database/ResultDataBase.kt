package com.neurotech.data.modules.storage.database

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.ResultStorage
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.ResultSourceCounterItem
import com.neurotech.data.modules.storage.database.entity.ResultTimeAndPeak
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResultDataBase: ResultStorage {

    @Inject
    lateinit var dao: ResultDao

    init {
        component.inject(this)
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

    override suspend fun setStressCauseByTime(stressCause: String, time: List<String>) {
        dao.setStressCauseByTime(stressCause, time)
    }

    override suspend fun saveResult(resultEntity: ResultEntity) {
        dao.insertResult(resultEntity)
    }

    override suspend fun getResultsInInterval(
        beginInterval: String,
        endInterval: String
    ): Flow<List<ResultEntity>> {
        return dao.getResultsInInterval(beginInterval,endInterval)
    }
}