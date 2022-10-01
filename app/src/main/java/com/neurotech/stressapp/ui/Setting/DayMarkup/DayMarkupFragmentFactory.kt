package com.neurotech.stressapp.ui.Setting.DayMarkup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.markupdata.AddMarkup
import com.neurotech.domain.usecases.markupdata.DeleteMarkup
import com.neurotech.domain.usecases.markupdata.GetMarkupListFlow
import javax.inject.Inject

class DayMarkupFragmentFactory @Inject constructor(
    val addMarkupUseCase: AddMarkup,
    val deleteMarkupUseCase: DeleteMarkup,
    val getMarkupListFlow: GetMarkupListFlow,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DayMarkupViewModel(addMarkupUseCase, deleteMarkupUseCase, getMarkupListFlow) as T
    }
}