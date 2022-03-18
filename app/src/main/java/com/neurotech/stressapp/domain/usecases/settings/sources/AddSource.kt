package com.neurotech.stressapp.domain.usecases.settings.sources

import com.neurotech.stressapp.domain.repository.SourceCaseRepository
import com.neurotech.stressapp.domain.repository.StatisticCaseRepository

class AddSource(val repository: SourceCaseRepository) {
    operator fun invoke(source: String): String{
       return repository.addSource(source)
    }
}