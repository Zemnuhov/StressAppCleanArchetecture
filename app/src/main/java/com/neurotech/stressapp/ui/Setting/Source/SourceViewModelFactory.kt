package com.neurotech.stressapp.ui.Setting.Source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neurotech.domain.usecases.settings.AddStimulus
import com.neurotech.domain.usecases.settings.DeleteStimulus
import com.neurotech.domain.usecases.settings.GetStimulusList
import javax.inject.Inject

class SourceViewModelFactory @Inject constructor(
    private val getStimulusList: GetStimulusList,
    private val addStimulus: AddStimulus,
    private val deleteStimulus: DeleteStimulus
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SourceViewModel(getStimulusList, addStimulus, deleteStimulus) as T
    }
}