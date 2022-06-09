package com.neurotech.data.modules.bluetooth

import android.content.Context
import com.neurotech.data.modules.settings.Settings
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.data.modules.settings.SharedPrefSettings
//import com.welie.blessed.BluetoothCentralManager
//import com.welie.blessed.BluetoothPeripheral
import java.util.*


class  Bluetooth(private val context: Context) {
//    val serviceUUID: UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
//    val notificationDataUUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
//    val writePeaksUUID: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")
//    val writeTonicUUID: UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb")
//    val writeTimeUUID: UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb")
//
//    val central: BluetoothCentralManager = BluetoothCentralManager(context)
//    val settings: Settings = SettingsApi()
//    var _peripheral: BluetoothPeripheral? = central.getPeripheral(settings.getDevice())
//    val peripheral get() = _peripheral!!
}