package com.neurotech.stressapp.ui.Main.TonicItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.tonicdata.GetOneDayAvgFlow
import com.neurotech.domain.usecases.tonicdata.GetOneHourAvgFlow
import com.neurotech.domain.usecases.tonicdata.GetTenMinuteAvgFlow
import com.neurotech.stressapp.Singleton
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TonicItemFragmentViewModel(
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getOneHourAvgFlow: GetOneHourAvgFlow,
    private val getTenMinuteAvgFlow: GetTenMinuteAvgFlow,
    private val getOneDayAvgFlow: GetOneDayAvgFlow,
) : ViewModel() {

    private val _tonicValue = MutableLiveData<Int>()
    val tonicValue: LiveData<Int> get() = _tonicValue

    private val _avgTonic = MutableLiveData<Int>()
    val avgTonic: LiveData<Int> get() = _avgTonic

    private val jobList = mutableListOf<Job>()

    init {
        viewModelScope.launch {
            getTonicValueFlow.invoke().collect{
                _tonicValue.postValue(it.value)
            }
        }
    }

    fun setInterval(interval: String){
        when(interval){
            Singleton.TEN_MINUTE -> {
                stopJob()
                jobList.add(
                    viewModelScope.launch {
                        getTenMinuteAvgFlow.invoke().collect{
                            _avgTonic.postValue(it?:0)
                        }
                    }
                )
            }
            Singleton.HOUR -> {
                stopJob()
                jobList.add(
                    viewModelScope.launch {
                        getOneHourAvgFlow.invoke().collect{
                            _avgTonic.postValue(it?:0)
                        }
                    }
                )
            }
            Singleton.DAY -> {
                stopJob()
                jobList.add(
                    viewModelScope.launch {
                        getOneDayAvgFlow.invoke().collect{
                            _avgTonic.postValue(it?:0)
                        }
                    }
                )
            }
        }
    }

    private fun stopJob(){
        jobList.forEach { it.cancel() }
        jobList.clear()
    }
}