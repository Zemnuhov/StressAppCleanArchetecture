package com.neurotech.data.modules.storage.database

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.MarkupStorage
import com.neurotech.data.modules.storage.database.dao.MarkupDao
import com.neurotech.data.modules.storage.database.entity.MarkupEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkupDataBase: MarkupStorage {

    @Inject
    lateinit var dao: MarkupDao

    init {
        component.inject(this)
    }

    override suspend fun updateMarkup(markup: MarkupEntity) {
        dao.updateMarkup(markup)
    }

    override suspend fun insertMarkup(markup: MarkupEntity) {
        dao.insertMarkup(markup)
    }

    override suspend fun deleteMarkup(markup: MarkupEntity) {
        dao.deleteMarkup(markup)
    }

    override suspend fun getMarkupList(): Flow<List<MarkupEntity>> {
        return dao.getMarkupListFlow()
    }
}