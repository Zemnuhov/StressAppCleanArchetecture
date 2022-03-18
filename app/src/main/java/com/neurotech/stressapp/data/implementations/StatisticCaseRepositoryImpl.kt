package com.neurotech.stressapp.data.implementations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.stressapp.data.database.entity.ResultSourceCounterItem
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.repository.StatisticCaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticCaseRepositoryImpl : StatisticCaseRepository {
    @Inject
    lateinit var dataBase: AppDatabase
    private val sources = MutableLiveData<List<ResultSourceCounterItem>>()
    private val settings = SettingsApi()

    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun getSources(): LiveData<List<ResultSourceCounterItem>> {
        CoroutineScope(Dispatchers.IO).launch {
            dataBase.resultDao().getCountBySources(settings.getStimulusList())
                .collect {
                    val resultList = it.toMutableList()
                    settings.getStimulusList().forEach { stimulus ->
                        if(stimulus !in resultList.map {item -> item.source }){
                            resultList.add(ResultSourceCounterItem(stimulus,0))
                        }
                    }
                    sources.postValue(resultList)
                }
        }
        return sources
    }
}