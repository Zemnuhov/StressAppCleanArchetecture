package com.neurotech.domain.usecases.gsrdata

import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.repository.GsrDataRepository

class GetTonicValuesInMemory(val repository: GsrDataRepository) {
    operator fun invoke():List<TonicFlowDomainModel>{
        return repository.getTonicValueInMemory()
    }
}