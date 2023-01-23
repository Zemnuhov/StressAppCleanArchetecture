package com.neurotech.data.modules.bluetooth.writing

import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.minute
import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.nordicsemi.android.ble.ktx.stateAsFlow
import java.util.*
import javax.inject.Inject

class WritingDataToDevice: WritingData {

    @Inject
    lateinit var manager: AppBluetoothManager
    private val timeFormat = "HH:mm:ss"
    private val dateFormat = "yyyy-MM-dd"
    private var lastWriteTime = Tempo.now - 5.minute

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        RepositoryDI.component.inject(this)
        scope.launch {
            manager.stateAsFlow().collect{
                if(it.isReady){
                    writeTime(Tempo.now)
                }
            }
        }

    }

    override suspend fun writeTime(time: Date) {
        if(Tempo.now - 1.minute > lastWriteTime){
            lastWriteTime = Tempo.now
            val timeString = time.toString(timeFormat)
            val dateString = time.toString(dateFormat)
            val timeByteArray = timeString.toByteArray()
            val dateByteArray = dateString.toByteArray()
            if (timeByteArray.isNotEmpty() && dateByteArray.isNotEmpty()) {
                manager.writeDateTime(timeByteArray, dateByteArray)
            }
        }
    }

    override suspend fun writeNotifyFlag(isNotify: Boolean) {
        manager.writeNotifyFlag(isNotify)
    }
}