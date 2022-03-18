package com.neurotech.stressapp.domain.usecases.mainfunctions.statisticcase

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.ResultSourceCounterItem
import com.neurotech.stressapp.domain.repository.StatisticCaseRepository

class GetSourcesUseCase(private val repository:StatisticCaseRepository) {
    operator fun invoke():LiveData<List<ResultSourceCounterItem>>{
        return repository.getSources()
    }
}