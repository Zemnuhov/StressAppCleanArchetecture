package com.neurotech.domain.usecases.resultdata

import com.cesarferreira.tempo.*
import com.neurotech.domain.models.DayResultDomainModel
import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.repository.DayResultRepository
import com.neurotech.domain.repository.ResultDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class GetResultsInMonth(private val repository: DayResultRepository) {
    suspend operator fun invoke(month: Date, filToFull: Boolean): Flow<List<DayResultDomainModel>> {
        return flow {
            repository.getResultForTheDay(month.beginningOfMonth, month.endOfMonth).collect { dayResult ->
                val resultForTheDayList = dayResult.toMutableList()
                if(filToFull){
                    val thisYear = month.toString("yyyy").toInt()
                    val thisMonth = month.toString("MM").toInt()
                    val dayInMonth = month.endOfMonth.toString("dd").toInt()
                    val existingDates = dayResult.map { it.date }
                    for (day in 1..dayInMonth) {
                        val date =
                            Tempo.with(year = thisYear, month = thisMonth, day = day).beginningOfDay
                        if (date !in existingDates) {
                            resultForTheDayList.add(
                                DayResultDomainModel(
                                    date, 0, 0, 0, ""
                                )
                            )
                        }
                    }
                }
                emit(
                    resultForTheDayList
                )
            }
        }
    }
}
