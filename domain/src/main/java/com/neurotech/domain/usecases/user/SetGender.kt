package com.neurotech.domain.usecases.user

import com.neurotech.domain.repository.UserRepository

class SetGender(private val repository: UserRepository) {
    suspend operator fun invoke(gender: Boolean){
        repository.setGender(gender)
    }
}