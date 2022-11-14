package com.neurotech.stressapp.ui.statistic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarferreira.tempo.*
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.usecases.resultdata.GetResultsByInterval
import com.neurotech.domain.usecases.resultdata.SetKeepByTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class StatisticFragmentViewModel(
    private val getResults: GetResultsByInterval,
    private val setKeepByTime: SetKeepByTime
) : ViewModel() {

    companion object{
        const val DAY = "DAY"
        const val WEEK = "WEEK"
        const val MONTH = "MONTH"
    }
    private var _period = DAY
    val period: String get() = _period

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
        _period = DAY
        date = Tempo.now
        updateData()
    }

    fun setWeekResults(){
        _period = WEEK
        date = Tempo.now
        updateData()

    }

    fun setMonthResults(){
        _period = MONTH
        date = Tempo.now
        updateData()
    }

    fun goToPrevious(){
        when(period){
            DAY -> {
                date -= 1.day
                updateData()

            }
            WEEK -> {
                date -= 7.day
                updateData()
            }
            MONTH -> {
                date = date.beginningOfMonth - 1.minute
                updateData()
            }
        }


    }

    fun goToNext(){
        when(period){
            DAY -> {
                if(date.beginningOfDay != Tempo.now.beginningOfDay){
                    date += 1.day
                    updateData()
                }
            }
            WEEK -> {
                if(date.beginningOfDay != Tempo.now.beginningOfDay) {
                    date += 7.day
                    updateData()
                }
            }
            MONTH -> {
                if(date.beginningOfMonth != Tempo.now.beginningOfMonth) {
                    date = date.endOfMonth + 1.minute
                    updateData()
                }
            }
        }
    }



    private fun updateData(){
        job?.cancel()
        when(period){
            DAY ->{
                job = scope.launch {
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
            WEEK -> {
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
                    getResults.invoke(beginWeek,endWeek).collect {
                        _results.postValue(it)
                    }
                }
            }
            MONTH -> {
                job = scope.launch {
                    getResults.invoke(date.beginningOfMonth,date.endOfMonth).collect {
                        _results.postValue(it)
                    }
                }
                _dateFlow.postValue("${date.beginningOfMonth.toString(TimeFormat.dateFormat)} - ${date.endOfMonth.toString(TimeFormat.dateFormat)}")
            }
        }
    }

}