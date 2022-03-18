package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.database.entity.ResultSourceCounterItem
import com.neurotech.stressapp.domain.repository.StatisticCaseRepository
import com.neurotech.stressapp.domain.usecases.mainfunctions.statisticcase.GetSourcesUseCase
import javax.inject.Inject

class StatisticItemFragmentViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: StatisticCaseRepository

    val sourceCount: LiveData<List<ResultSourceCounterItem>>

    init {
        Singleton.daggerComponent.inject(this)
        sourceCount = GetSourcesUseCase(repository).invoke()
    }

}