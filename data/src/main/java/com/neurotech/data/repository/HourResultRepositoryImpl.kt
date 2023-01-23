package com.neurotech.data.repository

import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.dao.HourResultDao
import com.neurotech.data.modules.storage.database.entity.HourResultsEntity
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.HourResultDomain
import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.repository.HourResultRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class HourResultRepositoryImpl : HourResultRepository {

    @Inject
    lateinit var hourResultDao: HourResultDao

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        component.inject(this)
    }


    override suspend fun getHourResultsFlow(): Flow<List<HourResultDomain>> {
        return flow {
            hourResultDao.getHourResultsFlow().collect{
                emit(
                    it.map { hourResult->
                        HourResultDomain(
                            hourResult.date,
                            hourResult.peaks,
                            hourResult.peaksAvg,
                            hourResult.tonic,
                            hourResult.stressCause
                        )
                    }
                )
            }
        }

    }

    override suspend fun insertHourResult(hourResult: HourResultDomain) {
        hourResultDao.insertHourResult(
            HourResultsEntity(
                hourResult.date,
                hourResult.peaks,
                hourResult.peaksAvg,
                hourResult.tonic,
                hourResult.stressCause
            )
        )
    }

    override suspend fun insertHourResults(hourResults: List<HourResultDomain>) {
        hourResultDao.insertHourResults(
            hourResults.map {
                HourResultsEntity(
                    it.date,
                    it.peaks,
                    it.peaksAvg,
                    it.tonic,
                    it.stressCause
                )
            }
        )
    }

    override suspend fun getDates(): List<Date> {
        return hourResultDao.getDates().map { it.toDate(TimeFormat.dateTimeFormatDataBase) }
    }

    override suspend fun updateHourResult(hourResult: HourResultDomain) {
        hourResultDao.updateHourResult(
            HourResultsEntity(
                hourResult.date,
                hourResult.peaks,
                hourResult.peaksAvg,
                hourResult.tonic,
                hourResult.stressCause
            )
        )
    }

    override suspend fun getResultForTheHour(
        beginInterval: Date,
        endInterval: Date
    ): Flow<List<ResultForTheDayDomainModel>> {
        return flow {
            hourResultDao.getResultForTheHourByInterval(
                beginInterval.toString(TimeFormat.dateTimeFormatDataBase),
                endInterval.toString(TimeFormat.dateTimeFormatDataBase)
            ).collect{
                emit(
                    it.map { hourResult->
                        ResultForTheDayDomainModel(
                            hourResult.date.toDate(TimeFormat.dateAndHourFormatDataBase),
                            hourResult.peaks,
                            hourResult.tonic,
                            hourResult.stressCause?: ""
                        )
                    }
                )
            }
        }
    }
}