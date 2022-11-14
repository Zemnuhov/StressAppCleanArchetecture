package com.neurotech.stressapp.ui.main.TonicItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.tonicdata.GetOneDayAvgFlow
import com.neurotech.domain.usecases.tonicdata.GetOneHourAvgFlow
import com.neurotech.domain.usecases.tonicdata.GetTenMinuteAvgFlow
import javax.inject.Inject

class TonicItemFragmentViewModelFactory @Inject constructor (
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getOneHourAvgFlow: GetOneHourAvgFlow,
    private val getTenMinuteAvgFlow: GetTenMinuteAvgFlow,
    private val getOneDayAvgFlow: GetOneDayAvgFlow,
        ):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TonicItemFragmentViewModel(
            getTonicValueFlow,
            getOneHourAvgFlow,
            getTenMinuteAvgFlow,
            getOneDayAvgFlow) as T
    }
}