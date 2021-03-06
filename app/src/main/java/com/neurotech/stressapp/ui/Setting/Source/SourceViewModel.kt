package com.neurotech.stressapp.ui.Setting.Source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.usecases.settings.AddStimulus
import com.neurotech.domain.usecases.settings.DeleteStimulus
import com.neurotech.domain.usecases.settings.GetStimulusList
import kotlinx.coroutines.launch

class SourceViewModel(
    private val getStimulusList: GetStimulusList,
    private val addStimulus: AddStimulus,
    private val deleteStimulus: DeleteStimulus
) : ViewModel() {


    private val _sourcesList = MutableLiveData<List<String>>()
    val sourcesList: LiveData<List<String>> get() = _sourcesList

    init {
        viewModelScope.launch {
            getStimulusList.invoke().collect{
                _sourcesList.postValue(it)
            }
        }

    }

    fun addSource(source: String){
        viewModelScope.launch {
            addStimulus.invoke(source)
        }
    }

    fun deleteSource(source: String){
        viewModelScope.launch {
            deleteStimulus.invoke(source)
        }
    }
}