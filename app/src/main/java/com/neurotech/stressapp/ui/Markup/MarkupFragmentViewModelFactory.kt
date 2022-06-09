package com.neurotech.stressapp.ui.Markup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.resultdata.GetGoingBeyondLimit
import com.neurotech.domain.usecases.resultdata.SetStressCauseByTime
import com.neurotech.domain.usecases.settings.GetStimulusList
import javax.inject.Inject

class MarkupFragmentViewModelFactory @Inject constructor(
    private val getGoingBeyondLimit: GetGoingBeyondLimit,
    private val getStimulusList: GetStimulusList,
    private val setStressCauseByTime: SetStressCauseByTime
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MarkupFragmentViewModel(
            getGoingBeyondLimit,
            getStimulusList,
            setStressCauseByTime
        ) as T
    }
}