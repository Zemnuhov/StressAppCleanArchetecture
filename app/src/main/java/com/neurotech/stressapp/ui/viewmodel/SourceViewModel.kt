package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.domain.repository.SourceCaseRepository
import com.neurotech.stressapp.domain.usecases.settings.sources.AddSource
import com.neurotech.stressapp.domain.usecases.settings.sources.DeleteSource
import com.neurotech.stressapp.domain.usecases.settings.sources.GetSourcesList
import javax.inject.Inject

class SourceViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: SourceCaseRepository

    val sourcesList: LiveData<List<String>>

    init {
        (application as App).component.inject(this)
        sourcesList = GetSourcesList(repository).invoke()
    }

    fun addSource(source: String): String{
        return AddSource(repository).invoke(source)
    }

    fun deleteSource(source: String){
        DeleteSource(repository).invoke(source)
    }
}