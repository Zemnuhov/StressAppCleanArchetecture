package com.neurotech.data.repository

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.syncdata.SyncData
import com.neurotech.data.modules.bluetooth.writing.WritingData
import com.neurotech.domain.repository.RecodingInDevice
import java.util.*
import javax.inject.Inject

class RecodingInDeviceImpl: RecodingInDevice {

    @Inject
    lateinit var writer: WritingData

    @Inject
    lateinit var syncData: SyncData

    init {
        component.inject(this)
    }

    override suspend fun recodingTime(time: Date) {
        writer.writeTime(time)
    }

    override suspend fun recodingNotifyFlag(isNotify: Boolean) {
        writer.writeNotifyFlag(isNotify)
    }
}