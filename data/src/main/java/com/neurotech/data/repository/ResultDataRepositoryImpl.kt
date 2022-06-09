package com.neurotech.data.repository

import android.util.Log
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.settings.Settings
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.ResultSourceCounterItem
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                        resultEntity.time,
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
                }.sortedBy { it.count }.reversed())
            }
        }
    }

    override suspend fun writeResult(model: ResultDomainModel) {
        resultDao.insertResult(
            ResultEntity(
                model.time,
                model.peakCount,
                model.tonicAvg,
                model.conditionAssessment,
                model.stressCause
            )
        )
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
                            resultEntity.time,
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
}