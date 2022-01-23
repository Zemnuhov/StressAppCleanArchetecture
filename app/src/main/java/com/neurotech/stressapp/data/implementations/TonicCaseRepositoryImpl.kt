package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.stressapp.domain.repository.TonicCaseRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TonicCaseRepositoryImpl : TonicCaseRepository {
    @Inject
    lateinit var bleConnection: BleConnection

    @Inject
    lateinit var dataBase: AppDatabase

    private val tonicValue = MutableLiveData<Int>()
    private val avgTonic = MutableLiveData<Int>()
    private val tonicDisposable = CompositeDisposable()
    private val dataBaseDisposable = CompositeDisposable()

    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun getAvgTonicValue(): LiveData<Int> {
        return avgTonic
    }

    override fun getTonicValue(): LiveData<Int> {
        setInterval(Singleton.TEN_MINUTE)
        tonicDisposable.add(bleConnection.tonicValueObservable.subscribe {
            tonicValue.postValue(it["value"] as Int)
        })
        return tonicValue
    }

    override fun setInterval(interval: String) {
        when (interval) {
            Singleton.TEN_MINUTE -> {
                dataBaseDisposable.clear()
                dataBaseDisposable.add(dataBase.tonicDao()
                    .getTenMinuteAvg()
                    .subscribe {
                        avgTonic.postValue(it)
                    }
                )
            }
            Singleton.HOUR -> {
                dataBaseDisposable.clear()
                dataBaseDisposable.add(dataBase.tonicDao()
                    .getOneHourAvg()
                    .subscribe {
                        avgTonic.postValue(it)
                    }
                )
            }
            Singleton.DAY -> {
                dataBaseDisposable.clear()
                dataBaseDisposable.add(dataBase.tonicDao()
                    .getOneDayAvg()
                    .subscribe {
                        avgTonic.postValue(it)
                    }
                )
            }
        }
    }
}