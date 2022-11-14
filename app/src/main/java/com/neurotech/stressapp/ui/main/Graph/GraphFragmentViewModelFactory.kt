package com.neurotech.stressapp.ui.main.Graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.gsrdata.GetPhaseValuesInMemory
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.gsrdata.GetTonicValuesInMemory
import com.neurotech.domain.usecases.settings.GetThreshold
import javax.inject.Inject

class GraphFragmentViewModelFactory @Inject constructor(
    private val getPhaseValueFlow: GetPhaseValueFlow,
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getPhaseValuesInMemory: GetPhaseValuesInMemory,
    private val getTonicValuesInMemory: GetTonicValuesInMemory,
    private val getThreshold: GetThreshold
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GraphFragmentViewModel(getPhaseValueFlow, getTonicValueFlow, getPhaseValuesInMemory, getTonicValuesInMemory, getThreshold) as T
    }
}