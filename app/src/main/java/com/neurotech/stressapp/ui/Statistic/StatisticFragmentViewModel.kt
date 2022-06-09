package com.neurotech.stressapp.ui.Statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarferreira.tempo.*
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.usecases.resultdata.GetResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatisticFragmentViewModel(
    private val getResults: GetResults
) : ViewModel() {

    val results: LiveData<List<ResultDomainModel>> get() = _results
    private val _results = MutableLiveData<List<ResultDomainModel>>()

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        setDayResults()
    }


    fun setDayResults(){
        job?.cancel()
        job = scope.launch {
            getResults.invoke().collect {
                _results.postValue(
                    it.filter { result ->
                        1.day.ago.before(result.time.toDate(TimeFormat.dateTimeFormatDataBase))
                    }.reversed()
                )
            }
        }
    }

    fun setWeekResults(){
        job?.cancel()
        job = scope.launch {
            getResults.invoke().collect {
                _results.postValue(
                    it.filter { result ->
                        1.week.ago.before(result.time.toDate(TimeFormat.dateTimeFormatDataBase))
                    }.reversed()
                )
            }
        }
    }

    fun setMonthResults(){
        job?.cancel()
        job = scope.launch {
            getResults.invoke().collect {
                _results.postValue(
                    it.filter {result ->
                        30.day.ago.before(result.time.toDate(TimeFormat.dateTimeFormatDataBase))
                    }.reversed()
                )
            }
        }
        //TODO("Доделать месяц")
    }

}