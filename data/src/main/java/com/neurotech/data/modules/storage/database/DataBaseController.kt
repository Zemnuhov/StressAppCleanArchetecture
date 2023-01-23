package com.neurotech.data.modules.storage.database

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cesarferreira.tempo.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.dao.DayResultDao
import com.neurotech.data.modules.storage.database.dao.HourResultDao
import com.neurotech.data.modules.storage.database.dao.ResultDao
import com.neurotech.data.modules.storage.database.dao.UserDao
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.UserEntity
import com.neurotech.data.modules.storage.firebase.FirebaseAPI
import com.neurotech.domain.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseController(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var resultDao: ResultDao

    @Inject
    lateinit var hourResultDao: HourResultDao

    @Inject
    lateinit var dayResultDao: DayResultDao

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseData: FirebaseAPI

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        component.inject(this)
    }

    private suspend fun fillLocalDataBase(
        resultInFirebase: List<ResultEntity>,
        resultInLocalBase: List<ResultEntity>
    ) = withContext(Dispatchers.IO) {

        val timeList = resultInLocalBase.map { it.time }
        resultInFirebase.forEach {
            if (it.time !in timeList) {
                resultDao.insertResult(it)
            }
        }

    }

    private suspend fun fillFirebaseData(
        resultInLocalBase: List<ResultEntity>,
        resultInFirebase: List<ResultEntity>
    ) = withContext(Dispatchers.IO) {
        appLog("Push data to firebase storage")
        val timeList = resultInFirebase.map { it.time }
        val writeList = mutableListOf<ResultEntity>()
        resultInLocalBase.forEach {
            if (it.time !in timeList) {
                writeList.add(it)
            }
        }
        firebaseData.writeTenMinuteResults(writeList)
    }


    private suspend fun fillDayResult() = withContext(Dispatchers.IO) {
        val dayResult = resultDao.getResultForTheDay(
            (Tempo.now - 1.year).toString(TimeFormat.dateTimeFormatDataBase),
            Tempo.now.endOfDay.toString(TimeFormat.dateTimeFormatDataBase)
        )
        val existingData = dayResultDao.getDates()
        dayResult.forEach {
            if (it.date !in existingData) {
                dayResultDao.insertDayResult(it)
            }
        }
        val dayResultsInWeek = resultDao.getResultForTheDay(
            (Tempo.now - 7.day).toString(TimeFormat.dateTimeFormatDataBase),
            Tempo.now.endOfDay.toString(TimeFormat.dateTimeFormatDataBase)
        )
        dayResultDao.updateDayResult(*dayResultsInWeek.toTypedArray())
    }

    private suspend fun fillHourResult() = withContext(Dispatchers.IO) {
        val hourResults = resultDao.getResultForTheHour(
            (Tempo.now - 1.year).toString(TimeFormat.dateTimeFormatDataBase),
            Tempo.now.toString(TimeFormat.dateTimeFormatDataBase)
        )
        val existingData = hourResultDao.getDates()
        hourResults.forEach {
            if (it.date !in existingData) {
                hourResultDao.insertHourResult(it)
            }
        }
        val hourResultsInDay = resultDao.getResultForTheHour(
            (Tempo.now - 1.day).toString(TimeFormat.dateTimeFormatDataBase),
            Tempo.now.toString(TimeFormat.dateTimeFormatDataBase)
        )
        hourResultDao.updateHourResult(*hourResultsInDay.toTypedArray())
    }

    private suspend fun updateUserParameters() = withContext(Dispatchers.IO) {
        val userParam = resultDao.getUserParameter()
        val user = userDao.getUser()
        val authUser = firebaseAuth.currentUser
        val resultUser = if (user != null && authUser != null) {
            UserEntity(
                authUser.uid,
                authUser.displayName ?: "",
                user.dateOfBirth,
                user.gender,
                (userParam.tonic * 0.8).toInt(),
                (userParam.dayPhase * 0.8).toInt(),
                (userParam.hourPhase * 0.8).toInt(),
                (userParam.tenMinutePhase * 0.8).toInt()
            )
        } else if (user != null) {
            UserEntity(
                user.id,
                user.name,
                user.dateOfBirth,
                user.gender,
                (userParam.tonic * 0.8).toInt(),
                (userParam.dayPhase * 0.8).toInt(),
                (userParam.hourPhase * 0.8).toInt(),
                (userParam.tenMinutePhase * 0.8).toInt()
            )
        } else {
            UserEntity(
                "1",
                "Тут будет имя",
                null,
                null,
                (userParam.tonic * 0.8).toInt(),
                (userParam.tenMinutePhase * 0.8).toInt(),
                (userParam.hourPhase * 0.8).toInt(),
                (userParam.dayPhase * 0.8).toInt()
            )
        }
        userDao.unregisterUser()
        userDao.insertUser(resultUser)
        if (authUser != null) {
            val ref = firebaseDatabase.getReference("users").child(authUser.uid)
            val userEntity = UserEntity(
                authUser.uid,
                authUser.displayName ?: "",
                resultUser.dateOfBirth,
                resultUser.gender,
                (userParam.tonic * 0.8).toInt(),
                (userParam.dayPhase * 0.8).toInt(),
                (userParam.hourPhase * 0.8).toInt(),
                (userParam.tenMinutePhase * 0.8).toInt()
            )
            ref.setValue(userEntity)
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val results = resultDao.getResult().first()
            appLog("Control DataBase ${results.size}")
            if (firebaseAuth.currentUser != null) {
                val resultsInFirebase = firebaseData.readTenMinuteResults()
                fillLocalDataBase(resultsInFirebase, results)
                fillFirebaseData(results, resultsInFirebase)
            }
            fillHourResult()
            fillDayResult()
            updateUserParameters()
        } catch (e: Exception) {
            appLog("Control DataBase $e")
            return@withContext Result.retry()
        }

        return@withContext Result.success()
    }


}