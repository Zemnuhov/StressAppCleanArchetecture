package com.neurotech.stressapp.ui.Statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarferreira.tempo.*
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.usecases.resultdata.GetResultsByInterval
import com.neurotech.domain.usecases.resultdata.GetResultsCountAndSourceInInterval
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatisticFragmentViewModel(
    private val getResults: GetResultsByInterval
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
            getResults.invoke(Tempo.now.beginningOfDay, Tempo.now.endOfDay).collect {
                _results.postValue(it)
            }
        }
    }

    fun setWeekResults(){
        job?.cancel()
        job = scope.launch {
            getResults.invoke(Tempo.now.beginningOfMonth,Tempo.now.endOfMonth).collect {
                _results.postValue(it)
            }
        }
    }

    fun setMonthResults(){
        job?.cancel()
        job = scope.launch {
            getResults.invoke(Tempo.now.beginningOfMonth,Tempo.now.endOfMonth).collect {
                _results.postValue(it)
            }
        }
        //TODO("Доделать месяц")
    }

}