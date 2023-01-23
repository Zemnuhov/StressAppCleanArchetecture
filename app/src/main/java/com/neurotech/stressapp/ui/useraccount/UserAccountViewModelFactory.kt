package com.neurotech.stressapp.ui.useraccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetUserParameterInInterval
import com.neurotech.domain.usecases.user.GetUser
import com.neurotech.domain.usecases.user.SetDateOfBirth
import com.neurotech.domain.usecases.user.SetGender
import javax.inject.Inject

class UserAccountViewModelFactory @Inject constructor(
    private val getUserParameterInInterval: GetUserParameterInInterval,
    private val getUser: GetUser,
    private val setDateOfBirth: SetDateOfBirth,
    private val setGender: SetGender
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAccountViewModel(getUserParameterInInterval,getUser,setDateOfBirth,setGender) as T
    }
}