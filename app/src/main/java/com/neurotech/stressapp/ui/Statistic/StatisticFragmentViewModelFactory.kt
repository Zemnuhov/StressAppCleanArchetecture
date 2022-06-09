package com.neurotech.stressapp.ui.Statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetResults
import javax.inject.Inject

class StatisticFragmentViewModelFactory @Inject constructor (val getResults: GetResults): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatisticFragmentViewModel(getResults) as T
    }
}