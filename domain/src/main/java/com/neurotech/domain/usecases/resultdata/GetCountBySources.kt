package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow

class GetCountBySources(private val repository: ResultDataRepository) {
    suspend operator fun invoke(sources: List<String>): Flow<List<ResultCountSourceDomainModel>>{
        return repository.getCountBySources(sources)
    }
}