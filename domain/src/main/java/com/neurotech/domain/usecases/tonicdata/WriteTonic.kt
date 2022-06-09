package com.neurotech.domain.usecases.tonicdata

import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.repository.TonicRepository

class WriteTonic(private val repository: TonicRepository) {
    suspend operator fun invoke(model: TonicFlowDomainModel) {
        repository.writeTonic(model)
    }
}
