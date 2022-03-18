package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.ResultEntity
import com.neurotech.stressapp.data.database.entity.ResultTimeAndPeak

interface PhaseCaseRepository {
    fun getPeakCount(): LiveData<Int>
    fun setInterval(interval: String)
    fun getResultsTenMinute():LiveData<List<ResultTimeAndPeak>>
}