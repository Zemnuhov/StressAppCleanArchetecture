package com.neurotech.data.modules.bluetooth

import java.util.*

object ListUUID {
    val dataServiceUUID: UUID = UUID.fromString("1556b7b0-f1b6-4bc3-8880-035e1299a745")
    val phaseFlowUUID: UUID = UUID.fromString("1556b7b1-f1b6-4bc3-8880-035e1299a745")
    val tonicFlowUUID: UUID = UUID.fromString("1556b7b2-f1b6-4bc3-8880-035e1299a745")
    val timeUUID: UUID = UUID.fromString("1556b7b3-f1b6-4bc3-8880-035e1299a745")
    val dateUUID: UUID = UUID.fromString("1556b7b4-f1b6-4bc3-8880-035e1299a745")
    val characteristicServiceUUID: UUID = UUID.fromString("1556b7b5-f1b6-4bc3-8880-035e1299a745")

    val memoryServiceUUID: UUID  = UUID.fromString("bacdabd0-ba2c-4e38-86ed-b35684fd3bb1")
    val memoryCharacteristicUUID: UUID = UUID.fromString("bacdabd1-ba2c-4e38-86ed-b35684fd3bb1")
    val memoryTimeBeginCharacteristicUUID: UUID = UUID.fromString("bacdabd2-ba2c-4e38-86ed-b35684fd3bb1")
    val memoryTimeEndCharacteristicUUID: UUID = UUID.fromString("bacdabd3-ba2c-4e38-86ed-b35684fd3bb1")
    val memoryDateEndCharacteristicUUID: UUID = UUID.fromString("bacdabd4-ba2c-4e38-86ed-b35684fd3bb1")
    val memoryMaxPeakValueCharacteristicUUID: UUID = UUID.fromString("bacdabd5-ba2c-4e38-86ed-b35684fd3bb1")
    val memoryTonicCharacteristicUUID: UUID = UUID.fromString("bacdabd6-ba2c-4e38-86ed-b35684fd3bb1")
}