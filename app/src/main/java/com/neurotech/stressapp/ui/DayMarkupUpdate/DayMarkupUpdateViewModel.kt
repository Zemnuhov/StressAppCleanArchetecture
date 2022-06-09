package com.neurotech.stressapp.ui.DayMarkupUpdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.usecases.markupdata.UpdateMarkup
import com.neurotech.domain.usecases.settings.GetStimulusList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class DayMarkupUpdateViewModel(
    private val getStimulusList: GetStimulusList,
    private val updateMarkupUseCase: UpdateMarkup
) : ViewModel() {


    private val _sources = MutableLiveData<List<String>>()
    val sources: LiveData<List<String>> get() = _sources

    init {
        viewModelScope.launch {
            getStimulusList.invoke().collect{
                _sources.postValue(it)
            }
        }
    }

    suspend fun updateMarkup(markup: MarkupDomainModel): String{
        return viewModelScope.async(Dispatchers.IO) {
            return@async updateMarkupUseCase.invoke(markup)
        }.await()
    }
}