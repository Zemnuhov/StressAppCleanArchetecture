package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import com.neurotech.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetCountBySources(
    private val resultDataRepository: ResultDataRepository,
    private val settingsRepository: SettingsRepository
    ) {
    suspend operator fun invoke(sources: List<String>): Flow<List<ResultCountSourceDomainModel>>{
        return flow {
            resultDataRepository.getCountBySources(sources).collect{
                val listSource = it.toMutableList()
                settingsRepository.getStimulusList().forEach { stimulus ->
                    if (stimulus !in listSource.map { item -> item.source }) {
                        listSource.add(ResultCountSourceDomainModel(stimulus, 0))
                    }
                }
                emit(listSource.sortedBy { it.count }.reversed())
            }
        }
    }
}