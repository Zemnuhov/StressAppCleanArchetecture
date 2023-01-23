package com.neurotech.stressapp.ui.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetResultForTheHourByInterval
import com.neurotech.domain.usecases.resultdata.GetResultsByInterval
import com.neurotech.domain.usecases.resultdata.GetResultsInMonth
import com.neurotech.domain.usecases.resultdata.SetKeepByTime
import com.neurotech.domain.usecases.user.GetUser
import javax.inject.Inject

class StatisticFragmentViewModelFactory @Inject constructor (
    private val getResults: GetResultsByInterval,
    private val setKeepByTime: SetKeepByTime,
    private val getResultForTheHourByInterval: GetResultForTheHourByInterval,
    private val getResultsInMonth: GetResultsInMonth,
    private val getUser: GetUser
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatisticFragmentViewModel(getResults,setKeepByTime, getResultForTheHourByInterval, getResultsInMonth, getUser) as T
    }
}