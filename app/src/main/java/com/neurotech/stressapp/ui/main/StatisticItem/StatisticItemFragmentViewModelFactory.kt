package com.neurotech.stressapp.ui.main.StatisticItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetCountBySources
import com.neurotech.domain.usecases.resultdata.GetResults
import com.neurotech.domain.usecases.settings.GetStimulusList
import javax.inject.Inject

class StatisticItemFragmentViewModelFactory @Inject constructor(
    private val getCountBySources: GetCountBySources,
    private val getStimulusList: GetStimulusList,
    private val getResults: GetResults
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatisticItemFragmentViewModel(getCountBySources, getStimulusList, getResults) as T
    }
}