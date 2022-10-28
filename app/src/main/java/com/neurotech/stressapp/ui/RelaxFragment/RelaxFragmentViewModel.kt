package com.neurotech.stressapp.ui.RelaxFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarferreira.tempo.Tempo
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.domain.usecases.phasedata.GetPeaksInInterval
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RelaxFragmentViewModel(
    private val getTonicValueFlow: GetTonicValueFlow,
    private val getPeaksInInterval: GetPeaksInInterval
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val _tonicValue = MutableLiveData<Int>()
    val tonicValue: LiveData<Int> get() = _tonicValue

    private val _peaksInSession = MutableLiveData<Int>()
    val peaksInSession: LiveData<Int> get() = _peaksInSession

    private val _beginTonicValue = MutableLiveData<Int>()
    val beginTonicValue: LiveData<Int> get() = _beginTonicValue

    private val _tonicDifference = MutableLiveData<Int>()
    val tonicDifference: LiveData<Int> get() = _tonicDifference

    private val _timeSession = MutableLiveData<String>()
    val timeSession: LiveData<String> get() = _timeSession

    var isFirst = true
    private var timerCount = 0
    val time = Tempo.now


    init {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                timerCount += 1
                val minute: Int = timerCount / 60
                val seconds = timerCount % 60

                _timeSession.postValue(
                    "${
                        if (minute < 10) {
                            "0$minute"
                        } else {
                            minute.toString()
                        }
                    }:${
                        if (seconds < 10) {
                            "0$seconds"
                        } else {
                            seconds.toString()
                        }
                    }"
                )
            }
        }, 1000L, 1000L)

        scope.launch {
            getTonicValueFlow.invoke().collect {
                if (isFirst) {
                    _beginTonicValue.postValue(it.value)
                    isFirst = false
                }
                if (beginTonicValue.value != null) {
                    if (beginTonicValue.value!! - it.value > 0) {
                        _tonicDifference.postValue(beginTonicValue.value!! - it.value)
                    } else {
                        _tonicDifference.postValue(0)
                    }
                }
                _tonicValue.postValue(it.value)
            }
        }
        scope.launch {
            getPeaksInInterval.invoke(time).collect {
                _peaksInSession.postValue(it)
            }
        }
    }
}