package com.neurotech.stressapp.ui.Main.StatisticItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.usecases.resultdata.GetCountBySources
import com.neurotech.domain.usecases.settings.GetStimulusList
import kotlinx.coroutines.launch

class StatisticItemFragmentViewModel(
    getCountBySources: GetCountBySources,
    getStimulusList: GetStimulusList
) : ViewModel() {

    private val _sourceCount = MutableLiveData<List<ResultCountSourceDomainModel>>()
    val sourceCount: LiveData<List<ResultCountSourceDomainModel>> get() = _sourceCount

    init {
        viewModelScope.launch {
            getStimulusList.getFlow().collect{
                Log.e("VVV", it.toString())
                getCountBySources.invoke(it).collect{ countSourceList ->
                    _sourceCount.postValue(countSourceList)
                }
            }
        }
    }
}

