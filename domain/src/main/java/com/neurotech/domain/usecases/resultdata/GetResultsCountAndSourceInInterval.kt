package com.neurotech.domain.usecases.resultdata

import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.repository.ResultDataRepository
import com.neurotech.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class GetResultsCountAndSourceInInterval(
    private val resultDataRepository: ResultDataRepository,
    private val settingsRepository: SettingsRepository
    ) {
        suspend operator fun invoke(beginInterval: Date, endInterval:Date): Flow<List<ResultCountSourceDomainModel>> {
            return flow {
                resultDataRepository.getResultsCountAndSourceInInterval(
                    settingsRepository.getStimulusList(),
                    beginInterval,
                    endInterval).collect{
                    val sourceList = it.toMutableList()
                    settingsRepository.getStimulusList().forEach { stimulus ->
                        if (stimulus !in sourceList.map { entity -> entity.source }) {
                            sourceList.add(ResultCountSourceDomainModel(stimulus, 0))
                        }
                    }
                    emit(sourceList.sortedBy { it.source })
                }
            }

        }
}