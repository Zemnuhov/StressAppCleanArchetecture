package com.neurotech.stressapp.ui.Markup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.usecases.resultdata.GetGoingBeyondLimit
import com.neurotech.domain.usecases.resultdata.SetStressCauseByTime
import com.neurotech.domain.usecases.settings.GetStimulusList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
                getStimulusList.getFlow().collect{
                    _stimulus.postValue(it)
                }
            }
        }
    }

    fun setStressCause(source: String, time: List<Date>) {
        scope.launch {
            setStressCauseByTime.invoke(source, time)
        }
    }
}