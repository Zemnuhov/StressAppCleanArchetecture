package com.neurotech.domain.usecases.tonicdata

import com.neurotech.domain.repository.TonicRepository
import kotlinx.coroutines.flow.Flow

class GetOneDayAvgFlow(private val repository: TonicRepository) {
    suspend operator fun invoke(): Flow<Int?>{
        return repository.getOneDayAvgFlow()
    }
}