package com.neurotech.data.repository

import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.repository.TonicRepository
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TonicRepositoryImpl: TonicRepository {
    @Inject
    lateinit var tonicDao: TonicDao
    private val timeFormat = "yyyy-MM-dd HH:mm:ss.SSS"

    init {
        component.inject(this)
    }

    override suspend fun getTenMinuteAvgFlow(): Flow<Int?> {
        return tonicDao.getTenMinuteAvgFlow()
    }

    override suspend fun getTenMinuteAvg(): Int {
        return tonicDao.getTenMinuteAvg()
    }

    override suspend fun getOneHourAvgFlow(): Flow<Int?> {
        return tonicDao.getOneHourAvgFlow()
    }

    override suspend fun getOneDayAvgFlow(): Flow<Int?> {
        return tonicDao.getOneDayAvgFlow()
    }

    override suspend fun writeTonic(model: TonicFlowDomainModel) {
        tonicDao.insertTonicValue(TonicEntity(model.time.toString(timeFormat),model.value))
    }
}