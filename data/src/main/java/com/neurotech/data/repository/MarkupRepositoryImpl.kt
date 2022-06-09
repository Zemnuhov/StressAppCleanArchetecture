package com.neurotech.data.repository

import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.minute
import com.cesarferreira.tempo.plus
import com.cesarferreira.tempo.toDate
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.storage.database.entity.MarkupEntity
import com.neurotech.domain.Codes
import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.repository.MarkupRepository
import com.neurotech.data.modules.storage.database.dao.MarkupDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarkupRepositoryImpl : MarkupRepository {

    @Inject
    lateinit var markupDao: MarkupDao

    private val format = "HH:mm"

    init {
        component.inject(this)
    }

    private fun isValidTime(markup: MarkupDomainModel): Boolean {
        var valid = true
        val markupListInDataBase = markupDao.getMarkupList(markup.markupName)
        for (time in markupListInDataBase) {
            if (time.timeBegin == null || time.timeEnd == null) {
                continue
            }
            if (markup.timeBegin!!.toDate(format) + 1.minute in
                time.timeBegin!!.toDate(format)..time.timeEnd!!.toDate(format)
                ||
                markup.timeEnd!!.toDate(format) - 1.minute in
                time.timeBegin!!.toDate(format)..time.timeEnd!!.toDate(format)
            ) {
                valid = false
            }
        }
        return valid
    }


    override suspend fun updateMarkup(markup: MarkupDomainModel): String {
        if (markup.timeBegin!!.toDate("HH:MM") < markup.timeBegin!!.toDate("HH:MM")) {
            return "Error: событие закончилось раньше чем началось"
        }

        if (!isValidTime(markup)) {
            return Codes.markupError
        }
        markupDao.updateMarkup(
            MarkupEntity(
                markup.markupName,
                markup.timeBegin,
                markup.timeEnd,
                markup.firstSource,
                markup.secondSource
            )
        )
        return Codes.markupUpdate
    }


    override suspend fun insertMarkup(markup: MarkupDomainModel) {
        markupDao.insertMarkup(
            MarkupEntity(
                markup.markupName,
                markup.timeBegin,
                markup.timeEnd,
                markup.firstSource,
                markup.secondSource
            )
        )
    }

    override suspend fun deleteMarkup(markup: MarkupDomainModel) {
        markupDao.deleteMarkup(
            MarkupEntity(
                markup.markupName,
                markup.timeBegin,
                markup.timeEnd,
                markup.firstSource,
                markup.secondSource
            )
        )
    }

    override suspend fun getMarkupListFlow(): Flow<List<MarkupDomainModel>> {
        return flow {
            markupDao.getMarkupListFlow().collect {
                emit(it.map { markupEntity ->
                    MarkupDomainModel(
                        markupEntity.markupName,
                        markupEntity.timeBegin,
                        markupEntity.timeEnd,
                        markupEntity.firstSource,
                        markupEntity.secondSource
                    )
                })
            }
        }
    }

    override suspend fun getMarkupList(): List<MarkupDomainModel> {
        return markupDao
            .getMarkupList("")
            .map {
                MarkupDomainModel(
                    it.markupName,
                    it.timeBegin,
                    it.timeEnd,
                    it.firstSource,
                    it.secondSource)
            }
    }
}