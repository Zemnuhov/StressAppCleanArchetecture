package com.neurotech.stressapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.neurotech.stressapp.App
import com.neurotech.stressapp.data.database.entity.ResultTimeAndPeak
import com.neurotech.stressapp.domain.repository.PhaseCaseRepository
import com.neurotech.stressapp.domain.usecases.mainfunctions.phasecase.GetPeaksCountUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.phasecase.GetResultsTenMinuteUseCase
import com.neurotech.stressapp.domain.usecases.mainfunctions.phasecase.SetIntervalUseCase
import javax.inject.Inject

class PhaseItemFragmentViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: PhaseCaseRepository

    public val peakCount: LiveData<Int>
    public val resultsInHour: LiveData<List<ResultTimeAndPeak>>

    init{
        (application as App).component.inject(this)
        peakCount = GetPeaksCountUseCase(repository).invoke()
        resultsInHour = GetResultsTenMinuteUseCase(repository).invoke()
    }

    fun setInterval(interval: String){
        SetIntervalUseCase(repository).invoke(interval)
    }
}