package com.neurotech.data.repository

import com.cesarferreira.tempo.beginningOfDay
import com.cesarferreira.tempo.endOfDay
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.dao.DayResultDao
import com.neurotech.data.modules.storage.database.entity.DayResultEntity
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.DayResultDomainModel
import com.neurotech.domain.models.MaxParamDomainModel
import com.neurotech.domain.repository.DayResultRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class DayResultRepositoryImpl: DayResultRepository {
    @Inject
    lateinit var dayResultDao: DayResultDao

    init {
        component.inject(this)
    }

    override suspend fun getDayResultsFlow(): Flow<List<DayResultDomainModel>> {
        return flow {
            dayResultDao.getDayResultsFlow().collect{
                emit(
                    it.map {
                        it.mapToDomain()
                    }
                )
            }
        }
    }

    override suspend fun insertDayResult(dayResult: DayResultDomainModel) {
        dayResultDao.insertDayResult(
            DayResultEntity(
                dayResult.date.toString(TimeFormat.dateTimeFormatDataBase),
                dayResult.peaks,
                dayResult.peaksAvg,
                dayResult.tonic,
                dayResult.stressCause
            )
        )
    }

    override suspend fun getResultForTheDay(
        beginInterval: Date,
        endInterval: Date
    ): Flow<List<DayResultDomainModel>> {
        return flow {
            dayResultDao.getResultForTheDayByInterval(
                beginInterval.beginningOfDay.toString(TimeFormat.dateFormatDataBase),
                endInterval.endOfDay.toString(TimeFormat.dateFormatDataBase)
            ).collect{
                emit(
                    it.map {
                        it.mapToDomain()
                    }
                )
            }
        }
    }

    override suspend fun getLastFiveValidDay(): Flow<List<DayResultDomainModel>> {
        return flow {
            dayResultDao.getLastFiveValidDay().collect{
                emit(
                    it.map {
                        it.mapToDomain()
                    }
                )
            }
        }
    }

    override suspend fun getMaxParamAsync(): Deferred<MaxParamDomainModel> {
        return CoroutineScope(Dispatchers.IO).async{
            dayResultDao.getMaxParameters().mapToDomain()
        }
    }


}
