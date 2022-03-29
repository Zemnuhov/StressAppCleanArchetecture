package com.neurotech.test.storage.database

import android.content.Context
import com.neurotech.test.App
import com.neurotech.test.storage.MarkupStorage
import com.neurotech.test.storage.database.dao.MarkupDao
import com.neurotech.test.storage.database.entity.MarkupEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkupDataBase(context: Context): MarkupStorage {

    @Inject
    lateinit var dao: MarkupDao

    init {
        (context as App).component.inject(this)
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
        return dao.getMarkupList()
    }
}