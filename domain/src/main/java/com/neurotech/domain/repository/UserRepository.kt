package com.neurotech.domain.repository

import com.neurotech.domain.models.UserDomain
import com.neurotech.domain.models.UserParameterDomainModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface UserRepository {
    suspend fun getUser(): Flow<UserDomain>
    suspend fun registerUser(userDomain: UserDomain)
    suspend fun getUserParameters(): Flow<UserParameterDomainModel>
    suspend fun setDateOfBirth(date: Date)
    suspend fun setGender(gender: Boolean)
}