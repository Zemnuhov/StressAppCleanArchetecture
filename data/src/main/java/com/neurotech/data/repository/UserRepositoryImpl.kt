package com.neurotech.data.repository

import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.dao.UserDao
import com.neurotech.data.modules.storage.database.entity.UserEntity
import com.neurotech.data.modules.storage.firebase.FirebaseAPI
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.UserDomain
import com.neurotech.domain.models.UserParameterDomainModel
import com.neurotech.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl: UserRepository {
    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var firebaseAPI: FirebaseAPI

    init {
        component.inject(this)
    }


    override suspend fun getUser(): Flow<UserDomain> {
        return flow {
            userDao.getUserFlow().collect{
                emit(
                    if(it != null){
                        UserDomain(
                            it.id,
                            it.name,
                            if(it.dateOfBirth != null &&  it.dateOfBirth != "NULL"){it.dateOfBirth.toDate(TimeFormat.dateFormatDataBase)}else null,
                            if(it.gender != null){if(it.gender == "male"){"Муж"}else{"Жен"}}else null,
                            it.tonicAvg,
                            it.peakInDayNormal,
                            it.peakInHourNormal,
                            it.peakNormal
                        )
                    }else{
                        UserDomain(
                            "0",
                            "name",
                            null,
                            null,
                            3000,
                            1200,
                            100,
                            15)
                    }

                )
            }
        }
    }

    override suspend fun registerUser(userDomain: UserDomain) {
        userDao.insertUser(
            UserEntity(
                userDomain.id,
                userDomain.name,
                userDomain.dateOfBirth?.toString(TimeFormat.dateFormatDataBase),
                userDomain.gender,
                userDomain.tonicAvg,
                userDomain.peakInDayNormal,
                userDomain.peakInHourNormal,
                userDomain.peakNormal
            )
        )
    }


    override suspend fun getUserParameters(): Flow<UserParameterDomainModel> {
        return flow {
//            userDao.getUserParameters().collect{
//                emit(
//                    UserParameterDomainModel(
//                        it.tonic,
//                        it.tenMinutePhase,
//                        it.hourPhase,
//                        it.dayPhase
//                    )
//                )
//            }
        }
    }

    override suspend fun setDateOfBirth(date: Date) {
        userDao.setBirthDate(date.toString(TimeFormat.dateFormatDataBase))
        firebaseAPI.setUser(userDao.getUser()!!)
    }

    override suspend fun setGender(gender: Boolean) {
        userDao.setGender(
            if(gender){
                "male"
            }else{
                "female"
            }
        )
        firebaseAPI.setUser(userDao.getUser()!!)
    }
}