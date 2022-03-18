package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.ResultSourceCounterItem

interface StatisticCaseRepository {
    fun getSources(): LiveData<List<ResultSourceCounterItem>>
}