package com.neurotech.data.repository

import android.util.Log
import com.cesarferreira.tempo.toString
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.PeakDomainModel
import com.neurotech.domain.repository.PhaseRepository
import com.neurotech.test.storage.database.entity.PeakEntity
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class PhaseRepositoryImpl : PhaseRepository {

    @Inject
    lateinit var phaseDao: PeakDao

    init {
        component.inject(this)
    }

    override suspend fun getTenMinuteCountFlow(): Flow<Int> {
        return phaseDao.getTenMinuteCountFlow()
    }

    override suspend fun getTenMinuteCount(): Int {
        return phaseDao.getTenMinuteCount()
    }

    override suspend fun getOneHourCountFlow(): Flow<Int> {
        return phaseDao.getOneHourCountFlow()
    }

    override suspend fun getOneDayCountFlow(): Flow<Int> {
        return phaseDao.getOneDayCountFlow()
    }

    override suspend fun getPeaksInInterval(dateTime: Date): Flow<Int> {
        return phaseDao.getPeaksInInterval(dateTime.toString(TimeFormat.dateTimeFormatDataBase))
    }

    override suspend fun writePhase(model: PeakDomainModel) {
        try {
            phaseDao.insertPeak(PeakEntity(model.timeBegin, model.timeEnd, model.max))
        }catch (e:Exception){
            appLog(e.message.toString())
        }

    }
}