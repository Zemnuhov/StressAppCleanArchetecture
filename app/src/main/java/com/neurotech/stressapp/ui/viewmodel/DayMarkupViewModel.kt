package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupCaseRepository
import com.neurotech.stressapp.domain.usecases.settings.markup.AddMarkup
import com.neurotech.stressapp.domain.usecases.settings.markup.DeleteMarkup
import com.neurotech.stressapp.domain.usecases.settings.markup.GetMarkupList
import com.neurotech.stressapp.domain.usecases.settings.markup.UpdateMarkup
import javax.inject.Inject

class DayMarkupViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: MarkupCaseRepository
    val markupList: LiveData<List<MarkupEntity>>

    init {
        (application as App).component.inject(this)
        markupList = GetMarkupList(repository).invoke()
    }

    fun addMarkup(markupName: String){
        AddMarkup(repository).invoke(markupName)
    }

    fun updateMarkup(markup: MarkupEntity){
        UpdateMarkup(repository).invoke(markup)
    }

    fun deleteMarkup(markup: MarkupEntity){
        DeleteMarkup(repository).invoke(markup)
    }


}