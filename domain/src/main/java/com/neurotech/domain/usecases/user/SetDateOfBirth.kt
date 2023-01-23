package com.neurotech.domain.usecases.user

import com.neurotech.domain.repository.UserRepository
import java.util.Date

class SetDateOfBirth(private val repository: UserRepository) {
    suspend operator fun invoke(date: Date){
        repository.setDateOfBirth(date)
    }
}