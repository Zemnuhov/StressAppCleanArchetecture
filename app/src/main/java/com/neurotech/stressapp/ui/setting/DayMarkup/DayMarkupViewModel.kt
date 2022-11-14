package com.neurotech.stressapp.ui.setting.DayMarkup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.usecases.markupdata.AddMarkup
import com.neurotech.domain.usecases.markupdata.DeleteMarkup
import com.neurotech.domain.usecases.markupdata.GetMarkupListFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DayMarkupViewModel(
    private val addMarkupUseCase: AddMarkup,
    private val deleteMarkupUseCase: DeleteMarkup,
    private val getMarkupListFlow: GetMarkupListFlow,
) : ViewModel() {
    private val _markupList = MutableLiveData<List<MarkupDomainModel>>()
    val markupList: LiveData<List<MarkupDomainModel>> get() = _markupList

    init {
        viewModelScope.launch {
            getMarkupListFlow.invoke().collect{
                _markupList.postValue(it)
            }
        }
    }

    fun addMarkup(markupName: String){
        viewModelScope.launch(Dispatchers.IO) {
            addMarkupUseCase.invoke(MarkupDomainModel(markupName,null,null,null, null))
        }
    }


    fun deleteMarkup(markup: MarkupDomainModel){
        viewModelScope.launch(Dispatchers.IO) {
            deleteMarkupUseCase.invoke(markup)
        }
    }


}