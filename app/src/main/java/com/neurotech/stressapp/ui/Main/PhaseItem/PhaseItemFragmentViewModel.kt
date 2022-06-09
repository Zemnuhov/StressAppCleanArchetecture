package com.neurotech.stressapp.ui.Main.PhaseItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.models.ResultTimeAndPeakDomainModel
import com.neurotech.domain.usecases.phasedata.GetOneDayCountFlow
import com.neurotech.domain.usecases.phasedata.GetOneHourCountFlow
import com.neurotech.domain.usecases.phasedata.GetTenMinuteCountFlow
import com.neurotech.domain.usecases.resultdata.GetResultsInOneHour
import com.neurotech.stressapp.Singleton
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PhaseItemFragmentViewModel(
    private val getTenMinuteCountFlow: GetTenMinuteCountFlow,
    private val getOneHourCountFlow: GetOneHourCountFlow,
    private val getOneDayCountFlow: GetOneDayCountFlow,
    private val getResultsInOneHour: GetResultsInOneHour,
) : ViewModel() {

    private val _peakCount = MutableLiveData<Int>()
    val peakCount: LiveData<Int> get() = _peakCount

    private val _resultsInHour = MutableLiveData<List<ResultTimeAndPeakDomainModel>>()
    val resultsInHour: LiveData<List<ResultTimeAndPeakDomainModel>> get() = _resultsInHour

    private val jobList: MutableList<Job> = mutableListOf()

    init{
        viewModelScope.launch {
            getResultsInOneHour.invoke().collect{
                _resultsInHour.postValue(it)
            }
        }
    }

    fun setInterval(interval: String){
        when(interval){
            Singleton.TEN_MINUTE -> {
                jobStop()
                jobList.add(viewModelScope.launch {
                    getTenMinuteCountFlow.invoke().collect{
                        _peakCount.postValue(it)
                    }
                })
            }
            Singleton.HOUR -> {
                jobStop()
                jobList.add(viewModelScope.launch {
                    getOneHourCountFlow.invoke().collect{
                        _peakCount.postValue(it)
                    }
                })
            }
            Singleton.DAY -> {
                jobStop()
                jobList.add(viewModelScope.launch {
                    getOneDayCountFlow.invoke().collect{
                        _peakCount.postValue(it)
                    }
                })
            }
        }
    }

    private fun jobStop(){
        jobList.forEach { it.cancel() }
        jobList.clear()
    }
}