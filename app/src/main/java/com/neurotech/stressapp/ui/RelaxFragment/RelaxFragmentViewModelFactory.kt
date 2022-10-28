package com.neurotech.stressapp.ui.RelaxFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.phasedata.GetPeaksInInterval
import javax.inject.Inject

class RelaxFragmentViewModelFactory @Inject constructor(
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getPeaksInInterval: GetPeaksInInterval
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RelaxFragmentViewModel(
            getTonicValueFlow,
            getPeaksInInterval
        ) as T
    }
}