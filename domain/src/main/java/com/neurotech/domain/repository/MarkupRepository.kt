package com.neurotech.domain.repository

import com.neurotech.domain.models.MarkupDomainModel
import kotlinx.coroutines.flow.Flow

interface MarkupRepository {
    suspend fun updateMarkup(markup: MarkupDomainModel): String
    suspend fun insertMarkup(markup: MarkupDomainModel)
    suspend fun deleteMarkup(markup: MarkupDomainModel)
    suspend fun getMarkupListFlow(): Flow<List<MarkupDomainModel>>
    suspend fun getMarkupList(): List<MarkupDomainModel>

}