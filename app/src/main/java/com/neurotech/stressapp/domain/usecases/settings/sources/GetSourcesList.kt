package com.neurotech.stressapp.domain.usecases.settings.sources

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.repository.SourceCaseRepository

class GetSourcesList(val repository: SourceCaseRepository) {
    operator fun invoke(): LiveData<List<String>>{
        return repository.getSourceList()
    }
}