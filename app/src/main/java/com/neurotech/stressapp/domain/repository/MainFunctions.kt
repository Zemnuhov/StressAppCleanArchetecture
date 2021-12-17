package com.neurotech.stressapp.domain.repository

import androidx.lifecycle.LiveData
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.domain.SourceStressAndCountItem

interface MainFunctions {

    fun getAvgTonicValue(timeInterval:Long): LiveData<Int>

    fun getDeviceState(): String

    fun getListDevice(): LiveData<List<Device>>

    fun getNumberOfPeak(timeInterval:Long):LiveData<Int>

    fun getSourceStressAndCountItemList():LiveData<List<SourceStressAndCountItem>>

    fun getTonicValue():LiveData<Int>

    fun getValueForGraphPhase(): LiveData<Float>

    fun getValueForGraphTonic(): LiveData<Float>

    fun increaseCountSourceStressItem(sourceStressAndCountItem: SourceStressAndCountItem)

}