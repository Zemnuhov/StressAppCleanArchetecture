package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.data.database.entity.MarkupEntity

interface MarkupCaseRepository {
    fun addMarkup(markupName: String):String
    fun deleteMarkup(markup: MarkupEntity):String
    fun updateMarkup(markup: MarkupEntity):String
    fun getMarkupList(): LiveData<List<MarkupEntity>>
}