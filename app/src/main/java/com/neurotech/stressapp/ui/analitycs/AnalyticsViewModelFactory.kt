package com.neurotech.stressapp.ui.analitycs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetLastFiveValidDay
import com.neurotech.domain.usecases.resultdata.GetMaxParam
import com.neurotech.domain.usecases.resultdata.GetResultsCountAndSourceInInterval
import com.neurotech.domain.usecases.resultdata.GetResultsInMonth
import com.neurotech.domain.usecases.user.GetUser
import javax.inject.Inject

class AnalyticsViewModelFactory @Inject constructor(
    private val getResultsInMonth: GetResultsInMonth,
    private val getResultsCountAndSourceInInterval: GetResultsCountAndSourceInInterval,
    private val getLastFiveValidDay: GetLastFiveValidDay,
    private val getUser: GetUser,
    private val getMaxParam: GetMaxParam
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnalyticsViewModel(
            getResultsInMonth,
            getResultsCountAndSourceInInterval,
            getLastFiveValidDay,
            getUser,
            getMaxParam
        ) as T
    }
}