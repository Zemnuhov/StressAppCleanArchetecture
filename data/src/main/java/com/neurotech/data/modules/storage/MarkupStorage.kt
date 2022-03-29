package com.neurotech.test.storage

import com.neurotech.test.storage.database.entity.MarkupEntity
import kotlinx.coroutines.flow.Flow

interface MarkupStorage {
    suspend fun updateMarkup(markup: MarkupEntity)
    suspend fun insertMarkup(markup: MarkupEntity)
    suspend fun deleteMarkup(markup: MarkupEntity)
    suspend fun getMarkupList(): Flow<List<MarkupEntity>>
}