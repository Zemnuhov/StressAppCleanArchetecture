package com.neurotech.data.repository

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.writing.WritingData
import com.neurotech.domain.repository.RecodingInDevice
import java.util.*
import javax.inject.Inject

class RecodingInDeviceImpl: RecodingInDevice {

    @Inject
    lateinit var writer: WritingData

    init {
        component.inject(this)
    }

    override suspend fun recodingPeaks(peaks: Int) {
        writer.writePeaks(peaks)
    }

    override suspend fun recodingTonic(value: Int) {
        writer.writeTonic(value)
    }

    override suspend fun recodingTime(time: Date) {
        writer.writeTime(time)
    }
}