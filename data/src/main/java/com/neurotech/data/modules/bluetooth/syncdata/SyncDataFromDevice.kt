package com.neurotech.data.modules.bluetooth.syncdata

import android.util.Log
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toString
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.TimeFormat.dateTimeFormatDataBase
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.test.storage.database.entity.TonicEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SyncDataFromDevice: SyncData {

    @Inject
    lateinit var manager: AppBluetoothManager
    @Inject
    lateinit var peaksDataBase: PeakDao
    @Inject
    lateinit var tonicDataBase: TonicDao
    val scope = CoroutineScope(Dispatchers.IO)
    init {
        RepositoryDI.component.inject(this)
        scope.launch{
            manager.memoryStateFlow.collect{
                if(it == 1){
                    getPeakFromDeviceInMemory()
                }
                if(it == 2){
                    getPeakFromDevice()
                }
            }
        }
        scope.launch {
            manager.memoryTonicFlow.collect{
                tonicDataBase.insertTonicValue(TonicEntity(Tempo.now.toString(dateTimeFormatDataBase), it))
                appLog("Write tonic value in background")
            }
        }

    }

    private suspend fun getPeakFromDeviceInMemory() = coroutineScope {
        val peaksFromDevice = manager.getPeaks()
        if(peaksFromDevice != null){
            try {
                peaksDataBase.insertPeak(peaksFromDevice)
            }catch (e:Exception){
                appLog(e.message.toString())
            }
        }
        appLog(peaksFromDevice.toString())
        delay(500)
        manager.writeMemoryFlag()
    }

    private suspend fun getPeakFromDevice() = coroutineScope {
        val peaksFromDevice = manager.getPeaks()
        if(peaksFromDevice != null){
            try {
                peaksDataBase.insertPeak(peaksFromDevice)
            }catch (e:Exception){
                appLog(e.message.toString())
            }
        }
        appLog(peaksFromDevice.toString())
    }
}