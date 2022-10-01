package com.neurotech.stressapp.ui.Analitycs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.models.ResultForTheDayDomainModel
import com.neurotech.domain.usecases.resultdata.GetResultsCountAndSourceInInterval
import com.neurotech.domain.usecases.resultdata.GetResultsInMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class AnalyticsViewModel(
    private val getResultsInMonth: GetResultsInMonth,
    private val getResultsCountAndSourceInInterval: GetResultsCountAndSourceInInterval,
): ViewModel() {
    private val _resultsInMonth = MutableLiveData<List<ResultForTheDayDomainModel>>()
    val resultsInMonth: LiveData<List<ResultForTheDayDomainModel>> get() = _resultsInMonth

    private val _resultsInInterval = MutableLiveData<List<ResultCountSourceDomainModel>>()
    val resultsInInterval: LiveData<List<ResultCountSourceDomainModel>> get() = _resultsInInterval

    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        scope.launch {
                getResultsInMonth.invoke().collect {
                    _resultsInMonth.postValue(it)
                }
        }
    }

    fun setInterval(beginInterval: Date, endInterval: Date){
        scope.launch {
            getResultsCountAndSourceInInterval.invoke(beginInterval, endInterval).collect{
                _resultsInInterval.postValue(it)
            }
        }
    }
}