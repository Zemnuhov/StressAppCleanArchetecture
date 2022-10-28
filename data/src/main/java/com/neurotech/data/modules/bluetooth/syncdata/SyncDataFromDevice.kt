package com.neurotech.data.modules.bluetooth.syncdata

import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import com.neurotech.data.modules.storage.database.dao.PeakDao
import kotlinx.coroutines.*
import javax.inject.Inject

class SyncDataFromDevice: SyncData {

    @Inject
    lateinit var manager: AppBluetoothManager
    @Inject
    lateinit var peaksDataBase: PeakDao
    val scope = CoroutineScope(Dispatchers.IO)
    init {
        RepositoryDI.component.inject(this)
        scope.launch{
            manager.memoryStateFlow.collect{
                if(it == 1){
                    getPeakFromDevice()
                }
            }
        }

    }

    private suspend fun getPeakFromDevice() = coroutineScope {
        val peaksFromDevice = manager.getPeaks()
        if(peaksFromDevice != null){
            peaksDataBase.insertPeak(peaksFromDevice)
        }
        appLog(peaksFromDevice.toString())
        delay(500)
        manager.writeMemoryFlag()
    }
}