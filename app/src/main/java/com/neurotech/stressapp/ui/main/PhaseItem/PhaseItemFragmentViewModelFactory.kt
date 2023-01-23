package com.neurotech.stressapp.ui.main.PhaseItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.PrimaryKey
import com.neurotech.domain.usecases.phasedata.GetOneDayCountFlow
import com.neurotech.domain.usecases.phasedata.GetOneHourCountFlow
import com.neurotech.domain.usecases.phasedata.GetTenMinuteCountFlow
import com.neurotech.domain.usecases.resultdata.GetResultsInOneHour
import com.neurotech.domain.usecases.user.GetUser
import javax.inject.Inject

class PhaseItemFragmentViewModelFactory @Inject constructor(
    private val getTenMinuteCountFlow: GetTenMinuteCountFlow,
    private val getOneHourCountFlow: GetOneHourCountFlow,
    private val getOneDayCountFlow: GetOneDayCountFlow,
    private val getResultsInOneHour: GetResultsInOneHour,
    private val getUser: GetUser
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhaseItemFragmentViewModel(
            getTenMinuteCountFlow,
            getOneHourCountFlow,
            getOneDayCountFlow,
            getResultsInOneHour,
            getUser
        ) as T
    }
}