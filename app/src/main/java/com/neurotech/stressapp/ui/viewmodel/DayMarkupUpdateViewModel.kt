package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupSettingsRepository
import com.neurotech.stressapp.domain.usecases.settings.markup.GetSourceListAsMarkup
import com.neurotech.stressapp.domain.usecases.settings.markup.UpdateMarkup
import com.neurotech.stressapp.domain.usecases.settings.sources.GetSourcesList
import javax.inject.Inject

class DayMarkupUpdateViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository:MarkupSettingsRepository
    val sources: LiveData<List<String>>

    init {
        (application as App).component.inject(this)
        sources = GetSourceListAsMarkup(repository).invoke()
    }

    fun updateMarkup(markup: MarkupEntity){
        UpdateMarkup(repository).invoke(markup)
    }


}