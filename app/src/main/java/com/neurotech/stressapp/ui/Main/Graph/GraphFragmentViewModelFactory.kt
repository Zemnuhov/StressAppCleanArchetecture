package com.neurotech.stressapp.ui.Main.Graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.settings.GetThreshold
import javax.inject.Inject

class GraphFragmentViewModelFactory @Inject constructor(
    private val getPhaseValueFlow: GetPhaseValueFlow,
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getThreshold: GetThreshold
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GraphFragmentViewModel(getPhaseValueFlow, getTonicValueFlow, getThreshold) as T
    }
}