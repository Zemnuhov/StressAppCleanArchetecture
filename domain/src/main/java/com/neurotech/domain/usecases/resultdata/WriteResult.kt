package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.repository.ResultDataRepository

class WriteResult (private val repository: ResultDataRepository) {
    suspend operator fun invoke(model: ResultDomainModel){
        if(model.tonicAvg != 0) {
            repository.writeResult(model)
        }
    }
}