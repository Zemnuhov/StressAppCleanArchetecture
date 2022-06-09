package com.neurotech.data.modules.storage.database

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.TonicStorage
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TonicDataBase: TonicStorage {

    @Inject
    lateinit var dao: TonicDao

    init {
        component.inject(this)
    }


    override suspend fun getTenMinuteAvgFlow(): Flow<Int?> {
        return dao.getTenMinuteAvgFlow()
    }

    override suspend fun getTenMinuteAvg(): Int {
        return dao.getTenMinuteAvg()
    }

    override suspend fun getOneHourAvgFlow(): Flow<Int?> {
        return dao.getOneHourAvgFlow()
    }

    override suspend fun getOneDayAvgFlow(): Flow<Int?> {
        return dao.getOneDayAvgFlow()
    }

    override suspend fun insertTonicValue(value: TonicEntity) {
        dao.insertTonicValue(value)
    }

    override suspend fun deleteTonicValue() {
        dao.deleteTonicValue()
    }
}