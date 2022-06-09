package com.neurotech.data.modules.bluetooth.writing

import android.content.Context
import android.util.Log
import com.cesarferreira.tempo.toString
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.di.RepositoryDI.Companion.component
//import com.neurotech.test.App
import com.neurotech.data.modules.bluetooth.Bluetooth
//import com.welie.blessed.WriteType
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class WritingDataDevice: WritingData {

    @Inject
    lateinit var bluetooth: Bluetooth
    private val timeFormat = "HH:mm:ss"

    init {
        component.inject(this)
    }

    override suspend fun writeTime(time: Date) {
//        val timeString = time.toString(timeFormat)
//        timeString.split("").forEach {
//            val timeByteArray = it.toByteArray(Charsets.UTF_8)
//            if (timeByteArray.isNotEmpty()) {
//                val result = bluetooth.peripheral.writeCharacteristic(
//                    bluetooth.serviceUUID,
//                    bluetooth.writeTimeUUID,
//                    timeByteArray,
//                    WriteType.WITH_RESPONSE
//                )
//                Log.i("WriteTime", "Writing: $result")
//            }
//        }
    }

    override suspend fun writePeaks(peaks: Int) {
//        val byteValue = ByteBuffer.allocate(4).putInt(peaks).array()
//        byteValue.reverse()
//        val result = bluetooth.peripheral.writeCharacteristic(
//            bluetooth.serviceUUID,
//            bluetooth.writePeaksUUID,
//            byteValue,
//            WriteType.WITH_RESPONSE
//        )
//        Log.i("WritePeaks", "Writing: $result")
    }

    override suspend fun writeTonic(value: Int) {
//        val byteValue = ByteBuffer.allocate(4).putInt(value).array()
//        byteValue.reverse()
//        val result = bluetooth.peripheral.writeCharacteristic(
//            bluetooth.serviceUUID,
//            bluetooth.writeTonicUUID,
//            byteValue,
//            WriteType.WITH_RESPONSE
//        )
//        Log.i("WriteTonic", "Writing: $result")
    }
}