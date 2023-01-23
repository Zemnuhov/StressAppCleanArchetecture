package com.neurotech.stressapp.ui.analitycs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarferreira.tempo.*
import com.neurotech.domain.models.DayResultDomainModel
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.usecases.resultdata.GetLastFiveValidDay
import com.neurotech.domain.usecases.resultdata.GetMaxParam
import com.neurotech.domain.usecases.resultdata.GetResultsCountAndSourceInInterval
import com.neurotech.domain.usecases.resultdata.GetResultsInMonth
import com.neurotech.domain.usecases.user.GetUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt


class AnalyticsViewModel(
    private val getResultsInMonth: GetResultsInMonth,
    private val getResultsCountAndSourceInInterval: GetResultsCountAndSourceInInterval,
    private val getLastFiveValidDay: GetLastFiveValidDay,
    private val getUser: GetUser,
    private val getMaxParam: GetMaxParam
): ViewModel() {
    private val _resultsInMonth = MutableLiveData<List<DayResultDomainModel>>()
    val resultsInMonth: LiveData<List<DayResultDomainModel>> get() = _resultsInMonth

    private val _resultsInInterval = MutableLiveData<List<ResultCountSourceDomainModel>>()
    val resultsInInterval: LiveData<List<ResultCountSourceDomainModel>> get() = _resultsInInterval

    private val _dayRatingList = MutableLiveData<List<Double>>()
    val dayRatingList: LiveData<List<Double>> get() = _dayRatingList

    private val _userRating = MutableLiveData<Int>()
    val userRating: LiveData<Int> get() = _userRating

    private val scope = CoroutineScope(Dispatchers.IO)

    private var _monthDate = Tempo.now
    val monthDate get() = _monthDate

    init {
        viewModelScope.launch {
            val user = getUser.invoke().first()
            getLastFiveValidDay.invoke().collect{
                val dayRating = it.map {
                    val tonicMapped = valueMapper(it.tonic, user.tonicAvg, getMaxParam.invoke().maxTonic, 1 , 5)
                    val dayPeaksMapped = valueMapper(it.peaks, user.peakInDayNormal, getMaxParam.invoke().maxPeakInDay, 1 , 5)
                    val avgPeaksMapped = valueMapper(it.peaksAvg, user.peakNormal, getMaxParam.invoke().maxPeaksInTenMinute, 1 , 5)
                    return@map (tonicMapped + dayPeaksMapped + avgPeaksMapped)/3
                }
                _dayRatingList.postValue(dayRating.reversed())
                _userRating.postValue((dayRating.sum()/dayRating.size).roundToInt())
            }
        }
    }

    private var monthJob: Job = scope.launch {
            getResultsInMonth.invoke(_monthDate,true).collect {
                _resultsInMonth.postValue(it)
            }
    }

    private fun observeData(){
        monthJob.cancel()
        monthJob = scope.launch {
            getResultsInMonth.invoke(_monthDate,true).collect {
                _resultsInMonth.postValue(it)
            }
        }
    }

    fun previousMonth(){
        _monthDate = _monthDate.beginningOfMonth - 1.minute
        observeData()
    }

    fun nextMonth(){
        if(_monthDate.beginningOfMonth != Tempo.now.beginningOfMonth){
            _monthDate = _monthDate.endOfMonth + 1.minute
            observeData()
        }
    }

    fun setInterval(beginInterval: Date, endInterval: Date){
        scope.launch {
            getResultsCountAndSourceInInterval.invoke(beginInterval, endInterval).collect{
                _resultsInInterval.postValue(it)
            }
        }
    }

    private fun valueMapper(value: Int, smin: Int, smax: Int, dmin: Int, dmax: Int): Double {

        return (value.toDouble() - smin.toDouble()) / (smax.toDouble() - smin.toDouble()) * (dmax.toDouble() - dmin.toDouble()) + dmin.toDouble()
    }
}