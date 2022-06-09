package com.neurotech.stressapp.ui.Markup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neurotech.domain.models.PhaseFlowDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.resultdata.GetGoingBeyondLimit
import com.neurotech.domain.usecases.resultdata.SetStressCauseByTime
import com.neurotech.domain.usecases.settings.GetStimulusList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MarkupFragmentViewModel(
    private val getGoingBeyondLimit: GetGoingBeyondLimit,
    private val getStimulusList: GetStimulusList,
    private val setStressCauseByTime: SetStressCauseByTime
) : ViewModel() {
    private val _tenMinuteResultBeyondLimit = MutableLiveData<List<ResultDomainModel>>()
    val tenMinuteResultBeyondLimit: LiveData<List<ResultDomainModel>> get() = _tenMinuteResultBeyondLimit

    private val _stimulus = MutableLiveData<List<String>>()
    val stimulus: LiveData<List<String>> get() = _stimulus

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            launch {
                getGoingBeyondLimit.invoke(15).collect {
                    _tenMinuteResultBeyondLimit.postValue(it)
                }
            }
            launch {
                getStimulusList.invoke().collect{
                    _stimulus.postValue(it)
                }
            }
        }
    }

    fun setStressCause(time: String, source: String) {
        scope.launch {
            setStressCauseByTime.invoke(source, listOf(time))
        }
    }
}