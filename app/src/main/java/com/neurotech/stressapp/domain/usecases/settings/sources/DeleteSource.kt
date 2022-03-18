package com.neurotech.stressapp.domain.usecases.settings.sources

import com.neurotech.stressapp.domain.repository.SourceCaseRepository

class DeleteSource(val repository: SourceCaseRepository) {
    operator fun invoke(source: String){
        repository.deleteSource(source)
    }
}