package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.repository.MarkupSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarkupSettingsRepositoryImpl: MarkupSettingsRepository {
    @Inject
    lateinit var database: AppDatabase
    val settings = SettingsApi()
    val sources = MutableLiveData<List<String>>()

    init {
        Singleton.daggerComponent.inject(this)
    }


    override fun getSources(): LiveData<List<String>> {
        sources.postValue(settings.getStimulusList())
        return sources
    }

    override fun updateMarkup(markup: MarkupEntity): String {
        CoroutineScope(Dispatchers.IO).launch {
            database.markupDao().updateMarkup(markup)
        }
        return "OK"
    }
}