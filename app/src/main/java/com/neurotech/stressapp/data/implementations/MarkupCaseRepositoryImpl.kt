package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.stressapp.data.database.entity.MarkupEntity
import com.neurotech.stressapp.domain.repository.MarkupCaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarkupCaseRepositoryImpl: MarkupCaseRepository {
    @Inject
    lateinit var database: AppDatabase
    private val markupList = MutableLiveData<List<MarkupEntity>>()



    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun addMarkup(markupName: String): String {
        CoroutineScope(Dispatchers.IO).launch {
            database.markupDao()
                .insertMarkup(MarkupEntity(markupName,null,null,null))
        }
        return "OK"
    }

    override fun deleteMarkup(markup: MarkupEntity): String {
        CoroutineScope(Dispatchers.IO).launch {
            database.markupDao().deleteMarkup(markup)
        }
        return "OK"
    }


    override fun getMarkupList(): LiveData<List<MarkupEntity>> {
        markupList.postValue(listOf())
        CoroutineScope(Dispatchers.IO).launch {
            database.markupDao().getMarkupList().collect{
                markupList.postValue(it)
            }
        }
        return markupList
    }
}