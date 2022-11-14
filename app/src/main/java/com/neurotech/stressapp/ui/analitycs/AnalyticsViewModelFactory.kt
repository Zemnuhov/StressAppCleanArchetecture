package com.neurotech.stressapp.ui.analitycs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetResultsCountAndSourceInInterval
import com.neurotech.domain.usecases.resultdata.GetResultsInMonth
import javax.inject.Inject

class AnalyticsViewModelFactory @Inject constructor (
    private val getResultsInMonth: GetResultsInMonth,
    private val getResultsCountAndSourceInInterval: GetResultsCountAndSourceInInterval
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnalyticsViewModel(getResultsInMonth, getResultsCountAndSourceInInterval) as T
    }
}