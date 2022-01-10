package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.SourceStressAndCountItem
import com.polidea.rxandroidble2.RxBleConnection

interface MainFunctions {

    fun getAvgTonicValue(timeInterval:Long): LiveData<Int>

    fun getDeviceState(): LiveData<Int>

    fun getNumberOfPeak(timeInterval:Long):LiveData<Int>

    fun getSourceStressAndCountItemList():LiveData<List<SourceStressAndCountItem>>

    fun getTonicValue():LiveData<Int>

    fun getValueForGraphPhase(): LiveData<Float>

    fun getValueForGraphTonic(): LiveData<Float>

    fun increaseCountSourceStressItem(sourceStressAndCountItem: SourceStressAndCountItem)

    fun disconnectDevice()

}