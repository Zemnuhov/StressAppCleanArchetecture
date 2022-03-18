package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.stressapp.data.database.entity.ResultTimeAndPeak
import com.neurotech.stressapp.domain.repository.PhaseCaseRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class PhaseCaseRepositoryImpl: PhaseCaseRepository {

    @Inject
    lateinit var dataBase: AppDatabase
    private var dataBaseIntervalJob: Job = Job()
    private val peakCounter = MutableLiveData<Int>()
    private val resultTimeAndPeak = MutableLiveData<List<ResultTimeAndPeak>>()

    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun getPeakCount(): LiveData<Int> {
        setInterval(Singleton.TEN_MINUTE)
        return peakCounter
    }

    override fun setInterval(interval: String) {
        when(interval){
            Singleton.TEN_MINUTE -> {
                dataBaseIntervalJob.cancel()
                dataBaseIntervalJob = CoroutineScope(Dispatchers.IO).launch {
                    dataBase.peakDao().getTenMinuteCountFlow().collect{
                        peakCounter.postValue(it)
                    }
                    launch {
                        while (true){
                            delay(30_000)
                            peakCounter.postValue(dataBase.peakDao().getTenMinuteCount())
                        }
                    }
                }
            }
            Singleton.HOUR -> {
                dataBaseIntervalJob.cancel()
                dataBaseIntervalJob = CoroutineScope(Dispatchers.IO).launch {
                    dataBase.peakDao().getOneHourCountFlow().collect{
                        peakCounter.postValue(it)
                    }
                }
            }
            Singleton.DAY -> {
                dataBaseIntervalJob.cancel()
                dataBaseIntervalJob = CoroutineScope(Dispatchers.IO).launch {
                    dataBase.peakDao().getOneDayCountFlow().collect{
                        peakCounter.postValue(it)
                    }
                }
            }
        }
    }

    override fun getResultsTenMinute(): LiveData<List<ResultTimeAndPeak>> {
        CoroutineScope(Dispatchers.IO).launch{
            dataBase.resultDao().getResultsInOneHour().collect{
                resultTimeAndPeak.postValue(it)
            }
        }
        return resultTimeAndPeak
    }
}