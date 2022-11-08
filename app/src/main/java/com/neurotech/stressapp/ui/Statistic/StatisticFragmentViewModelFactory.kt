package com.neurotech.stressapp.ui.Statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetResultsByInterval
import com.neurotech.domain.usecases.resultdata.SetKeepByTime
import javax.inject.Inject

class StatisticFragmentViewModelFactory @Inject constructor (
    private val getResults: GetResultsByInterval,
    private val setKeepByTime: SetKeepByTime
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatisticFragmentViewModel(getResults,setKeepByTime) as T
    }
}