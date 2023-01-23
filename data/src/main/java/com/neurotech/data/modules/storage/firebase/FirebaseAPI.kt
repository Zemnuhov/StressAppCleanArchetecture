package com.neurotech.data.modules.storage.firebase

import com.neurotech.data.modules.storage.database.entity.DayResultEntity
import com.neurotech.data.modules.storage.database.entity.HourResultsEntity
import com.neurotech.data.modules.storage.database.entity.ResultEntity
import com.neurotech.data.modules.storage.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface FirebaseAPI {
    suspend fun getUserInfo(): Flow<UserEntity?>
    suspend fun setUser(user: UserEntity)
    suspend fun writeTenMinuteResult(result: ResultEntity)
    suspend fun writeTenMinuteResults(results: List<ResultEntity>)
    suspend fun readTenMinuteResults():List<ResultEntity>
}