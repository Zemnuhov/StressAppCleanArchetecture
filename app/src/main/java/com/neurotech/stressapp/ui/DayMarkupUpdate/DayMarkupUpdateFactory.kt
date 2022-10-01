package com.neurotech.stressapp.ui.DayMarkupUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.markupdata.UpdateMarkup
import com.neurotech.domain.usecases.settings.GetStimulusList
import javax.inject.Inject

class DayMarkupUpdateFactory @Inject constructor(
    private val getStimulusList: GetStimulusList,
    private val updateMarkupUseCase: UpdateMarkup
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DayMarkupUpdateViewModel(getStimulusList,updateMarkupUseCase) as T
    }
}