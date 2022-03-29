package com.neurotech.test.storage.database

import android.content.Context
import com.neurotech.test.App
import com.neurotech.test.storage.TonicStorage
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TonicDataBase(context: Context):TonicStorage {

    @Inject
    lateinit var dao: TonicDao

    init {
        (context as App).component.inject(this)
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