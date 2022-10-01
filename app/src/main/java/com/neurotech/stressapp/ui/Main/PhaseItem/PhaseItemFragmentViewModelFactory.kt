package com.neurotech.stressapp.ui.Main.PhaseItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.phasedata.GetOneDayCountFlow
import com.neurotech.domain.usecases.phasedata.GetOneHourCountFlow
import com.neurotech.domain.usecases.phasedata.GetTenMinuteCountFlow
import com.neurotech.domain.usecases.resultdata.GetResultsInOneHour
import javax.inject.Inject

class PhaseItemFragmentViewModelFactory @Inject constructor(
    private val getTenMinuteCountFlow: GetTenMinuteCountFlow,
    private val getOneHourCountFlow: GetOneHourCountFlow,
    private val getOneDayCountFlow: GetOneDayCountFlow,
    private val getResultsInOneHour: GetResultsInOneHour
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhaseItemFragmentViewModel(
            getTenMinuteCountFlow,
            getOneHourCountFlow,
            getOneDayCountFlow,
            getResultsInOneHour,
        ) as T
    }
}