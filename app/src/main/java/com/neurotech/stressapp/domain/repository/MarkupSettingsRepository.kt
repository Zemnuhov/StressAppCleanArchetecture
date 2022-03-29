package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.MarkupEntity

interface MarkupSettingsRepository {
    fun getSources(): LiveData<List<String>>
    fun updateMarkup(markup: MarkupEntity):String
}