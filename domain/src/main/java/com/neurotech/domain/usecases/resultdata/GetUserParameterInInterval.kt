package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.UserParameterDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class GetUserParameterInInterval(private val repository: ResultDataRepository) {
    suspend operator fun invoke(beginInterval: Date, endInterval: Date): Flow<UserParameterDomainModel>{
        return repository.getUserParameterInInterval(beginInterval, endInterval)
    }
}