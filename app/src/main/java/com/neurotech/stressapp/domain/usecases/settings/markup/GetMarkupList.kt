package com.neurotech.stressapp.domain.usecases.settings.markup

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupCaseRepository

class GetMarkupList(val repository: MarkupCaseRepository) {
    operator fun invoke():LiveData<List<MarkupEntity>>{
        return repository.getMarkupList()
    }
}