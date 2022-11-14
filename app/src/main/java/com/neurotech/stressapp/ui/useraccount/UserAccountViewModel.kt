package com.neurotech.stressapp.ui.useraccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarferreira.tempo.*
import com.neurotech.domain.models.UserParameterDomainModel
import com.neurotech.domain.usecases.resultdata.GetUserParameterInInterval
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserAccountViewModel(
    private val userParameterInInterval: GetUserParameterInInterval
): ViewModel() {

    private val _userParameter = MutableLiveData<UserParameterDomainModel>()
    val userParameter: LiveData<UserParameterDomainModel> get() = _userParameter
    private var userParameterFlowJob: Job? = null

    private val _normalTonic = MutableLiveData<Int>()
    val normalTonic: LiveData<Int> get() = _normalTonic

    private val _normalPeakTenMinute = MutableLiveData<Int>()
    val normalPeakTenMinute: LiveData<Int> get() = _normalPeakTenMinute

    private val _normalPeakDay = MutableLiveData<Int>()
    val normalPeakDay: LiveData<Int> get() = _normalPeakDay

    init {
        setOneDayInterval()
        viewModelScope.launch {
            userParameterInInterval(Tempo.now - 1.years, Tempo.now.endOfDay).collect{
                _normalTonic.postValue(it.tonicAverage - (it.tonicAverage*0.2).toInt())
                _normalPeakTenMinute.postValue(it.peaksInTenMinute - (it.peaksInTenMinute*0.2).toInt())
                _normalPeakDay.postValue(it.peaksInDay - (it.peaksInDay*0.2).toInt())
            }
        }

    }

    fun setOneDayInterval(){
        userParameterFlowJob?.cancel()
        userParameterFlowJob = viewModelScope.launch {
            userParameterInInterval(Tempo.now.beginningOfDay, Tempo.now.endOfDay).collect{
                _userParameter.postValue(it)
            }
        }
    }

    fun setOneMonthInterval(){
        userParameterFlowJob?.cancel()
        userParameterFlowJob = viewModelScope.launch {
            userParameterInInterval(Tempo.now - 31.day, Tempo.now.endOfDay).collect{
                _userParameter.postValue(it)
            }
        }
    }

    fun setOneYearInterval(){
        userParameterFlowJob?.cancel()
        userParameterFlowJob = viewModelScope.launch {
            userParameterInInterval(Tempo.now - 1.years, Tempo.now.endOfDay).collect{
                _userParameter.postValue(it)
            }
        }
    }

}