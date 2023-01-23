package com.neurotech.stressapp.ui.statistic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cesarferreira.tempo.*
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.UserDomain
import com.neurotech.domain.usecases.resultdata.GetResultForTheHourByInterval
import com.neurotech.domain.usecases.resultdata.GetResultsByInterval
import com.neurotech.domain.usecases.resultdata.GetResultsInMonth
import com.neurotech.domain.usecases.resultdata.SetKeepByTime
import com.neurotech.domain.usecases.user.GetUser
import com.neurotech.stressapp.Interval
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import java.util.*

class StatisticFragmentViewModel(
    private val getResults: GetResultsByInterval,
    private val setKeepByTime: SetKeepByTime,
    private val getResultForTheHourByInterval: GetResultForTheHourByInterval,
    private val getResultsInMonth: GetResultsInMonth,
    private val getUser: GetUser
) : ViewModel() {


    private var _period = Interval.DAY
    val period: Interval get() = _period

    private val _results = MutableLiveData<List<ResultDomainModel>>()
    val results: LiveData<List<ResultDomainModel>> get() = _results

    private val _dateFlow = MutableLiveData<String>()
    val dateFlow: LiveData<String> get() = _dateFlow

    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var date = Tempo.now
    private val xLabelOfDay = mutableListOf<String>().apply {
        var time = Tempo.now.beginningOfDay
        while (time < date.endOfDay){
            add(time.toString("HH:mm"))
            time += 10.minute
        }
    }

    var user :Deferred<UserDomain> = scope.async {
        return@async getUser.invoke().first()
    }


    var state = 1

    init {
        setDayResults()
        Log.e("Time", xLabelOfDay.toString())
        _dateFlow.postValue(date.toString("EE " + TimeFormat.dateFormat))
    }

    fun setKeepByTime(time: Date, keep:String?){
        scope.launch {
            setKeepByTime.invoke(keep, time)
        }
    }

    fun setDayResults(){
        _period = Interval.DAY
        date = Tempo.now
        updateData()
    }

    fun setWeekResults(){
        _period = Interval.WEEK
        date = Tempo.now
        updateData()

    }

    fun setMonthResults(){
        _period = Interval.MONTH
        date = Tempo.now
        updateData()
    }

    fun goToPrevious(){
        when(period){
            Interval.DAY -> {
                date -= 1.day
                updateData()

            }
            Interval.WEEK -> {
                date -= 7.day
                updateData()
            }
            Interval.MONTH -> {
                date = date.beginningOfMonth - 1.minute
                updateData()
            }
            else -> {}
        }


    }

    fun goToNext(){
        when(period){
            Interval.DAY -> {
                if(date.beginningOfDay != Tempo.now.beginningOfDay){
                    date += 1.day
                    updateData()
                }
            }
            Interval.WEEK -> {
                if(date.beginningOfDay != Tempo.now.beginningOfDay) {
                    date += 7.day
                    updateData()
                }
            }
            Interval.MONTH -> {
                if(date.beginningOfMonth != Tempo.now.beginningOfMonth) {
                    date = date.endOfMonth + 1.minute
                    updateData()
                }
            }
            else -> {}
        }
    }



    private fun updateData(){
        job?.cancel()
        when(period){
            Interval.DAY ->{
                job = scope.launch {
                    state = 1
                    getResults.invoke(date.beginningOfDay, date.endOfDay).collect {
                        val resultTimes = it.map { it.time.toString("HH:mm") }
                        val resultMutableList = it.toMutableList()
                        xLabelOfDay.forEach {
                            if(it !in resultTimes){
                                resultMutableList.add(ResultDomainModel(
                                    "${date.toString(TimeFormat.dateFormatDataBase)} $it:00.000".toDate(TimeFormat.dateTimeFormatDataBase),
                                    0,0,1)
                                )
                            }
                        }
                        _results.postValue(it.sortedBy { it.time })
                    }
                }
                _dateFlow.postValue(date.toString("EE " +TimeFormat.dateFormat))
            }
            Interval.WEEK -> {
                state = 2
                job = scope.launch {
                    val beginWeek: Date = if(date.isMonday){
                        date.beginningOfDay
                    }else{
                        var count = 1
                        while (!(date-count.day).isMonday ){
                            count++
                        }
                        (date-count.day).beginningOfDay
                    }
                    val endWeek = beginWeek + 7.day - 1.minute
                    _dateFlow.postValue("${beginWeek.toString(TimeFormat.dateFormat)} - ${endWeek.toString(TimeFormat.dateFormat)}")
                    getResultForTheHourByInterval.invoke(beginWeek,endWeek).collect {
                        _results.postValue(it.map {
                            ResultDomainModel(
                                it.date,
                                it.peaks,
                                it.tonic,
                                1,
                                it.stressCause
                            )
                        })
                    }
                }
            }
            Interval.MONTH -> {
                state = 3
                job = scope.launch {
                    getResultsInMonth.invoke(date.beginningOfMonth,false).collect {
                        _results.postValue(it.map {
                            ResultDomainModel(
                                it.date,
                                it.peaks,
                                it.tonic,
                                1,
                                it.stressCause
                            )
                        })
                    }
                }
                _dateFlow.postValue("${date.beginningOfMonth.toString(TimeFormat.dateFormat)} - ${date.endOfMonth.toString(TimeFormat.dateFormat)}")
            }
            else -> {}
        }
    }

}