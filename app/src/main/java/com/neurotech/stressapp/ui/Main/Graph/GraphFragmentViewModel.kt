package com.neurotech.stressapp.ui.Main.Graph

import androidx.lifecycle.*
import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.gsrdata.GetPhaseValuesInMemory
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.gsrdata.GetTonicValuesInMemory
import com.neurotech.domain.usecases.settings.GetThreshold
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GraphFragmentViewModel(
    private val getPhaseValueFlow: GetPhaseValueFlow,
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getPhaseValuesInMemory: GetPhaseValuesInMemory,
    private val getTonicValuesInMemory: GetTonicValuesInMemory,
    private val getThreshold: GetThreshold
) : ViewModel() {

    private val _phaseValue = MutableLiveData<PhaseFlowDomainModel>()
    val phaseValue: LiveData<PhaseFlowDomainModel> get() = _phaseValue
    private val _tonicValue = MutableLiveData<TonicFlowDomainModel>()
    val tonicValue: LiveData<TonicFlowDomainModel> get() = _tonicValue
    val threshold = getThreshold.invoke()
    val phaseValuesInMemory get() =  getPhaseValuesInMemory.invoke()
    val tonicValuesInMemory get() =  getTonicValuesInMemory.invoke()

    init {
        viewModelScope.launch {
                getPhaseValueFlow.invoke().collect {
                    _phaseValue.postValue(it)
                }
        }
        viewModelScope.launch {
            getTonicValueFlow.invoke().collect{
                _tonicValue.postValue(it)
            }
        }
    }
}