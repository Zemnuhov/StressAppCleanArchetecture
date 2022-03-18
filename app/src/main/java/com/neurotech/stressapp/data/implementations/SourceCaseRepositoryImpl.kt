package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.repository.SourceCaseRepository

class SourceCaseRepositoryImpl : SourceCaseRepository {

    private val sourcesList = MutableLiveData<List<String>>()
    private val settings = SettingsApi()

    override fun addSource(source: String): String {
        if(settings.getStimulusList().size<7){
            if(source.isNotEmpty()) {
                settings.addStimulus(source)
                sourcesList.postValue(settings.getStimulusList())
                return "$source добавлено!"
            }
        }
        return "Невозможно добавить больше источников!"
    }

    override fun deleteSource(source: String) {
        settings.deleteStimulus(source)
        sourcesList.postValue(settings.getStimulusList())
    }

    override fun getSourceList(): LiveData<List<String>> {
        sourcesList.postValue(settings.getStimulusList())
        return sourcesList
    }
}