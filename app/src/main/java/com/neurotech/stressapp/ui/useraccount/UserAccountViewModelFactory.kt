package com.neurotech.stressapp.ui.useraccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetUserParameterInInterval
import javax.inject.Inject

class UserAccountViewModelFactory @Inject constructor(
    private val getUserParameterInInterval: GetUserParameterInInterval
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAccountViewModel(getUserParameterInInterval) as T
    }
}