package com.neurotech.stressapp.ui.main.StatisticItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toString
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.ResultCountSourceDomainModel
import com.neurotech.domain.usecases.resultdata.GetCountBySources
import com.neurotech.domain.usecases.resultdata.GetResults
import com.neurotech.domain.usecases.settings.GetStimulusList
import com.neurotech.stressapp.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class StatisticItemFragmentViewModel(
    getCountBySources: GetCountBySources,
    getStimulusList: GetStimulusList,
    private val getResults: GetResults
) : ViewModel() {

    private val _sourceCount = MutableLiveData<List<ResultCountSourceDomainModel>>()
    val sourceCount: LiveData<List<ResultCountSourceDomainModel>> get() = _sourceCount

    private val _isRecoding = MutableLiveData<Boolean>()
    val isRecoding: LiveData<Boolean> get() = _isRecoding

    init {
        viewModelScope.launch {
            Singleton.recoding.collect{
                _isRecoding.postValue(it)
            }
        }
        viewModelScope.launch {
            getStimulusList.getFlow().collect{
                Log.e("VVV", it.toString())
                getCountBySources.invoke(it).collect{ countSourceList ->
                    _sourceCount.postValue(countSourceList)
                }
            }
        }
    }

    suspend fun writeFileResults() = coroutineScope{
        launch(Dispatchers.IO) {
            val path = Singleton.context.getExternalFilesDir("files")
            val file = File(path, "TenMinutesResult ${Tempo.now.toString(TimeFormat.dateTimeFormatDataBase)}" + ".txt")
            file.createNewFile()
            file.appendText("dateTime, peakCount, tonicAvg, keep\n")
            getResults.invoke().first().forEach {
                file.appendText("${it.time.toString(TimeFormat.dateTimeFormatDataBase)}, ${it.peakCount}, ${it.tonicAvg}, ${it.keep}\n")
            }
        }

    }
}

