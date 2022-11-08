package com.neurotech.data.repository

import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.TonicStorage
import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.repository.TonicRepository
import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TonicRepositoryImpl: TonicRepository {
    @Inject
    lateinit var tonicData: TonicStorage
    private val timeFormat = "yyyy-MM-dd HH:mm:ss.SSS"

    init {
        component.inject(this)
    }

    override suspend fun getTenMinuteAvgFlow(): Flow<Int?> {
        return tonicData.getTenMinuteAvgFlow()
    }

    override suspend fun getTenMinuteAvg(): Int {
        return tonicData.getTenMinuteAvg()
    }

    override suspend fun getOneHourAvgFlow(): Flow<Int?> {
        return tonicData.getOneHourAvgFlow()
    }

    override suspend fun getOneDayAvgFlow(): Flow<Int?> {
        return tonicData.getOneDayAvgFlow()
    }

    override suspend fun writeTonic(model: TonicFlowDomainModel) {
        tonicData.insertTonicValue(TonicEntity(model.time.toString(timeFormat),model.value))
    }
}