package com.neurotech.stressapp.data.implementations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.neurotech.stressapp.domain.repository.GraphRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GraphRepositoryImpl: GraphRepository {

    @Inject
    lateinit var bleConnection: BleConnection

    private val tonicValue = MutableLiveData<HashMap<String,Any>>()
    private val phaseValue = MutableLiveData<HashMap<String,Any>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        Singleton.daggerComponent.inject(this)
    }

    override fun getTonicValue(): LiveData<HashMap<String,Any>> {
        compositeDisposable.add(bleConnection.tonicValueObservable.subscribe{
            tonicValue.postValue(it)
        })
        return tonicValue
    }

    override fun getPhaseValue(): LiveData<HashMap<String,Any>> {
        compositeDisposable.add(bleConnection.phaseValueObservable.subscribe{
            phaseValue.postValue(it)
        })
        return phaseValue
    }

    override fun getThreshold(): Double {
        return SettingsApi().getThreshold()
    }
}