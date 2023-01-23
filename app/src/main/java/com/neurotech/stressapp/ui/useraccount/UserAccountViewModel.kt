package com.neurotech.stressapp.ui.useraccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.cesarferreira.tempo.*
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.UserDomain
import com.neurotech.domain.models.UserParameterDomainModel
import com.neurotech.domain.usecases.resultdata.GetUserParameterInInterval
import com.neurotech.domain.usecases.user.GetUser
import com.neurotech.domain.usecases.user.SetDateOfBirth
import com.neurotech.domain.usecases.user.SetGender
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.util.Date

class UserAccountViewModel(
    private val userParameterInInterval: GetUserParameterInInterval,
    private val getUser: GetUser,
    private val setDateOfBirth: SetDateOfBirth,
    private val setGender: SetGender
): ViewModel() {

    private val _userParameter = MutableLiveData<UserParameterDomainModel>()
    val userParameter: LiveData<UserParameterDomainModel> get() = _userParameter
    private var userParameterFlowJob: Job? = null


    private val _user = MutableLiveData<UserDomain>()
    val user: LiveData<UserDomain> get() = _user

    init {
        setOneDayInterval()
        viewModelScope.launch {
            getUser.invoke().collect{
                _user.postValue(it)
            }
        }

    }

    fun setOneDayInterval(){
        userParameterFlowJob?.cancel()
        userParameterFlowJob = viewModelScope.launch {
            val begin = Tempo.now.beginningOfDay
            val end = Tempo.now.endOfDay
            userParameterInInterval(begin, end).collect{
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

    fun setDateOfBirth(date: Date){
        CoroutineScope(Dispatchers.IO).launch {
            setDateOfBirth.invoke(date)
        }
    }

    fun setGender(gender: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            setGender.invoke(gender)
        }
    }

}