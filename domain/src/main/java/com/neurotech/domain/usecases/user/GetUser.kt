package com.neurotech.domain.usecases.user

import com.neurotech.domain.models.UserDomain
import com.neurotech.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUser(private val repository: UserRepository) {
    suspend operator fun invoke(): Flow<UserDomain> {
        return repository.getUser()
    }

}