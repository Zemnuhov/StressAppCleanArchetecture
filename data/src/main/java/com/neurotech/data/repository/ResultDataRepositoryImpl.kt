package com.neurotech.data.repository

import android.util.Log
import com.cesarferreira.tempo.*
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.settings.Settings
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.ResultForTheDay
import com.neurotech.data.modules.storage.database.entity.ResultSourceCounterItem
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.TimeFormat.dateFormatDataBase
import com.neurotech.domain.TimeFormat.dateTimeFormatDataBase
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class ResultDataRepositoryImpl : ResultDataRepository {

    @Inject
    lateinit var resultDao: ResultDao
    @Inject
    lateinit var settings: Settings

    init {
        component.inject(this)
    }

    override suspend fun getResults(): Flow<List<ResultDomainModel>> {
        return flow {
            resultDao.getResult().collect {
                emit(it.map { resultEntity ->
                    ResultDomainModel(
                        resultEntity.time.toDate(dateTimeFormatDataBase).beginningOfMinute,
                        resultEntity.peakCount,
                        resultEntity.tonicAvg,
                        resultEntity.conditionAssessment,
                        resultEntity.stressCause
                    )
                }
                )
            }
        }
    }

    override suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeakDomainModel>> {
        return flow {
            resultDao.getResultsInOneHour().collect {
                emit(it.map { resultTimeAndPeak ->
                    ResultTimeAndPeakDomainModel(
                        resultTimeAndPeak.time,
                        resultTimeAndPeak.peakCount
                    )
                })
            }
        }
    }

    override suspend fun getResultsTenMinuteCount(): Int {
        return resultDao.getTenMinuteCount()
    }

    override suspend fun getCountBySources(sources: List<String>): Flow<List<ResultCountSourceDomainModel>> {
        return flow {
            resultDao.getCountBySources(sources).collect {
                val listSource = it.toMutableList()
                settings.getStimulusList().forEach { stimulus ->
                    if (stimulus !in listSource.map { item -> item.source }) {
                        listSource.add(ResultSourceCounterItem(stimulus, 0))
                    }
                }
                emit(listSource.map { resultSourceCounterItem ->
                    Log.e("ResultSourceCounterItem", resultSourceCounterItem.toString())
                    ResultCountSourceDomainModel(
                        resultSourceCounterItem.source,
                        resultSourceCounterItem.count
                    )
                }.sortedBy { entity -> entity.count }.reversed())
            }
        }
    }

    override suspend fun writeResult(model: ResultDomainModel) {
        if(model.tonicAvg != 0){
            resultDao.insertResult(
                ResultEntity(
                    model.time.toString(dateTimeFormatDataBase),
                    model.peakCount,
                    model.tonicAvg,
                    model.conditionAssessment,
                    model.stressCause
                )
            )
        }
    }

    override suspend fun setStressCauseByTime(stressCause: String, time: List<String>) {
        resultDao.setStressCauseByTime(stressCause, time)
    }

    override suspend fun getGoingBeyondLimit(peakLimit: Int): Flow<List<ResultDomainModel>> {
        return flow {
            resultDao.getStressResult(peakLimit).collect {
                emit(
                    it.map { resultEntity ->
                        ResultDomainModel(
                            resultEntity.time.toDate(dateTimeFormatDataBase).beginningOfMinute,
                            resultEntity.peakCount,
                            resultEntity.tonicAvg,
                            resultEntity.conditionAssessment,
                            resultEntity.stressCause
                        )
                    }
                )
            }
        }
    }

    override suspend fun getResultsInMonth(): Flow<List<ResultForTheDayDomainModel>> {
        return flow {
            val beginningOfMonth = Tempo.now.beginningOfMonth
            val endOfMonth = Tempo.now.endOfMonth
            resultDao.getResultForTheDay(
                beginningOfMonth.toString(dateFormatDataBase),
                endOfMonth.toString(dateFormatDataBase)
            ).collect {
                val resultForTheDayList = it.toMutableList()
                val datesList =
                    it.map { entity -> entity.date.toDate(dateFormatDataBase).beginningOfDay }
                (beginningOfMonth.toString("dd").toInt()..
                        endOfMonth.toString("dd").toInt()).forEach { dayNumber ->
                    val day = Tempo.with(
                        beginningOfMonth.toString("yyyy").toInt(),
                        beginningOfMonth.toString("MM").toInt(),
                        dayNumber
                    ).beginningOfDay
                    if (day !in datesList) {
                        resultForTheDayList.add(
                            ResultForTheDay(
                                day.toString(dateFormatDataBase), 0, 0, ""
                            )
                        )
                    }
                }
                emit(
                    resultForTheDayList.map { resultEntity ->
                        ResultForTheDayDomainModel(
                            resultEntity.date.toDate(dateFormatDataBase).beginningOfDay,
                            resultEntity.peaks,
                            resultEntity.tonic,
                            resultEntity.stressCause
                        )
                    }
                )
            }
        }
    }

    override suspend fun getResultsCountAndSourceInInterval(
        beginInterval: Date,
        endInterval: Date
    ): Flow<List<ResultCountSourceDomainModel>> {
        return flow {
            resultDao.getCountStressCauseInInterval(
                settings.getStimulusList(),
                beginInterval.toString(dateTimeFormatDataBase),
                endInterval.toString(dateTimeFormatDataBase)
            ).collect {
                val sourceList = it.toMutableList()
                settings.getStimulusList().forEach { stimulus ->
                    if (stimulus !in sourceList.map { entity -> entity.source }) {
                        sourceList.add(ResultSourceCounterItem(stimulus, 0))
                    }
                }
                emit(
                    sourceList.map { resultEntity ->
                        ResultCountSourceDomainModel(
                            resultEntity.source,
                            resultEntity.count
                        )
                    }.sortedBy { entity -> entity.source }
                )
            }
        }
    }

    override suspend fun getResultsInInterval(
        beginInterval: Date,
        endInterval: Date
    ): Flow<List<ResultDomainModel>> {
        return flow {
            resultDao.getResultsInInterval(
                beginInterval.toString(dateTimeFormatDataBase),
                endInterval.toString(dateTimeFormatDataBase)
            ).collect{
                emit(
                    it.map { entity ->
                        ResultDomainModel(
                            entity.time.toDate(dateTimeFormatDataBase).beginningOfMinute,
                            entity.peakCount,
                            entity.tonicAvg,
                            entity.conditionAssessment,
                            entity.stressCause
                        )
                    }
                )
            }
        }
    }
}