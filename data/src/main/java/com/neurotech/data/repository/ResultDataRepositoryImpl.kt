package com.neurotech.data.repository

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cesarferreira.tempo.beginningOfMinute
import com.cesarferreira.tempo.toString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.DataBaseController
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.firebase.FirebaseAPI
import com.neurotech.domain.TimeFormat.dateTimeFormatDataBase
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.models.UserParameterDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ResultDataRepositoryImpl : ResultDataRepository {

    @Inject
    lateinit var resultDao: ResultDao

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseData: FirebaseAPI

    @Inject
    lateinit var workManager: WorkManager

    init {
        component.inject(this)
        val dbControlRequest =
            PeriodicWorkRequestBuilder<DataBaseController>(1, TimeUnit.DAYS)
                .addTag("db_control")
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        workManager.cancelAllWorkByTag("db_control")
        workManager.enqueue(dbControlRequest)

    }

    override suspend fun getResults(): Flow<List<ResultDomainModel>> {
        return flow {
            resultDao.getResult().collect {
                emit(it.map { resultEntity ->
                    resultEntity.toDomain()
                })
            }
        }
    }

    override suspend fun getResultsInOneHour(): Flow<List<ResultTimeAndPeakDomainModel>> {
        return flow {
            resultDao.getResultsInOneHour().collect {
                emit(it.map { resultTimeAndPeak ->
                    ResultTimeAndPeakDomainModel(
                        resultTimeAndPeak.time, resultTimeAndPeak.peakCount
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
                emit(it.map { resultSourceCounterItem ->
                    ResultCountSourceDomainModel(
                        resultSourceCounterItem.source, resultSourceCounterItem.count
                    )
                })
            }
        }
    }

    override suspend fun writeResult(model: ResultDomainModel) {
        val result = ResultEntity(
            model.time.beginningOfMinute.toString(dateTimeFormatDataBase),
            model.peakCount,
            model.tonicAvg,
            model.conditionAssessment,
            model.stressCause
        )
        resultDao.insertResult(result)
        firebaseData.writeTenMinuteResult(result)
    }

    override suspend fun setStressCauseByTime(stressCause: String, time: List<Date>) {
        CoroutineScope(Dispatchers.IO).launch {
            resultDao.setStressCauseByTime(stressCause,
                time.map { it.toString(dateTimeFormatDataBase) })
            firebaseData.writeTenMinuteResults(resultDao.getResultInTimes(time.map {
                it.toString(
                    dateTimeFormatDataBase
                )
            }))
        }
    }

    override suspend fun setKeepByTime(keep: String?, time: Date) {
        resultDao.setKeepByTime(keep, time.toString(dateTimeFormatDataBase))
        firebaseData.writeTenMinuteResults(
            resultDao.getResultInTimes(
                listOf(
                    time.toString(
                        dateTimeFormatDataBase
                    )
                )
            )
        )
    }

    override suspend fun getGoingBeyondLimit(peakLimit: Int): Flow<List<ResultDomainModel>> {
        return flow {
            resultDao.getStressResult(peakLimit).collect {
                emit(it.map { resultEntity ->
                    resultEntity.toDomain()
                })
            }
        }
    }


    override suspend fun getResultsCountAndSourceInInterval(
        stimulusList: List<String>, beginInterval: Date, endInterval: Date
    ): Flow<List<ResultCountSourceDomainModel>> {
        return flow {
            resultDao.getCountStressCauseInInterval(
                stimulusList,
                beginInterval.toString(dateTimeFormatDataBase),
                endInterval.toString(dateTimeFormatDataBase)
            ).collect {
                emit(it.map { resultEntity ->
                    ResultCountSourceDomainModel(
                        resultEntity.source, resultEntity.count
                    )
                })
            }
        }
    }

    override suspend fun getResultsInInterval(
        beginInterval: Date, endInterval: Date
    ): Flow<List<ResultDomainModel>> {
        return flow {
            resultDao.getResultsInInterval(
                beginInterval.toString(dateTimeFormatDataBase),
                endInterval.toString(dateTimeFormatDataBase)
            ).collect {
                emit(it.map { entity ->
                    entity.toDomain()
                })
            }
        }
    }

    override suspend fun getUserParameterInInterval(
        beginInterval: Date, endInterval: Date
    ): Flow<UserParameterDomainModel> {
        firebaseData.getUserInfo()
        return flow {
            resultDao.getUserParameterInInterval(
                beginInterval.toString(dateTimeFormatDataBase), endInterval.toString(
                    dateTimeFormatDataBase
                )
            ).collect {
                emit(
                    UserParameterDomainModel(
                        it.tonic, it.tenMinutePhase, it.hourPhase, it.dayPhase
                    )
                )
            }
        }
    }

}