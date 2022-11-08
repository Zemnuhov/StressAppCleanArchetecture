package com.neurotech.stressapp.service

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.seconds
import com.cesarferreira.tempo.toString
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.models.PeakDomainModel
import com.neurotech.domain.models.ResultDomainModel
import com.neurotech.domain.models.TonicFlowDomainModel
import com.neurotech.domain.usecases.phasedata.GetTenMinuteCountPhase
import com.neurotech.domain.usecases.phasedata.WritePhase
import com.neurotech.domain.usecases.resultdata.GetTenMinuteResultCount
import com.neurotech.domain.usecases.resultdata.WriteResult
import com.neurotech.domain.usecases.settings.GetThreshold
import com.neurotech.domain.usecases.tonicdata.GetTenMinuteAvgTonic
import com.neurotech.domain.usecases.tonicdata.WriteTonic
import com.neurotech.stressapp.App
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.notification.NotificationBuilderApp
import com.neurotech.stressapp.notification.NotificationReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DataFlowAnalyzer(context: Context) {
    @Inject
    lateinit var getThreshold: GetThreshold
    @Inject
    lateinit var writeTonic: WriteTonic
    @Inject
    lateinit var writePhase: WritePhase
    @Inject
    lateinit var writeResult: WriteResult
    @Inject
    lateinit var getTenMinuteResultCount: GetTenMinuteResultCount
    @Inject
    lateinit var getTenMinutePhase: GetTenMinuteCountPhase
    @Inject
    lateinit var getTenMinuteTonic: GetTenMinuteAvgTonic
    @Inject
    lateinit var deviceBleWriter: DeviceBleWriter


    var notificationReceiver: NotificationReceiver
    private val notificationBuilderApp by lazy { NotificationBuilderApp(context) }
    private var lastInsertDatabase = Tempo.now
    private var isPeak = false
    private var beginPeak: Date? = null
    private var endPeak: Date? = null
    private var maxValue: Double = 0.0
    private var threshold: Double
    private val scope = CoroutineScope(Dispatchers.IO)


    init {
        (context.applicationContext as App).component.inject(this)
        threshold = getThreshold.invoke()
        notificationReceiver = NotificationReceiver()
        val filter = IntentFilter(ConnectivityManager.EXTRA_REASON).apply {
            addAction(NotificationReceiver.FIRST_SOURCE_ACTION)
            addAction(NotificationReceiver.SECOND_SOURCE_ACTION)
        }
        context.registerReceiver(notificationReceiver, filter)
        scope.launch {
            deviceBleWriter.recordTime(Tempo.now)
        }
    }

    fun putTonicFlow(tonicItem: TonicModelService) {
        if (Tempo.now - 30.seconds > lastInsertDatabase) {
            lastInsertDatabase = Tempo.now
            scope.launch {
                val time = tonicItem.time
                val value = tonicItem.value
                writeTonic.invoke(TonicFlowDomainModel(value, time))
                deviceBleWriter.recordTonic(value)
            }
        }
    }

    fun putPhaseFlow(phaseItem: PhaseModelService) {
        val time = phaseItem.time
        val value = phaseItem.value
        if (value > threshold && !isPeak) {
            beginPeak = time
            isPeak = true
        }
        if (value < threshold && isPeak) {
            endPeak = time
            scope.launch {
                try {
                    writePhase.invoke(
                        PeakDomainModel(
                            beginPeak!!.toString(TimeFormat.dateTimeFormatDataBase),
                            endPeak!!.toString(TimeFormat.dateTimeFormatDataBase),
                            maxValue
                        )
                    )
                    deviceBleWriter.recordPeaks(getTenMinutePhase.invoke())
                    maxValue = 0.0
                }catch (e: Exception){

                }
            }
            isPeak = false
        }
        if (isPeak && value > maxValue) {
            maxValue = value
        }
    }

    fun writeResultTenMinutes() {
        scope.launch {
            val time = Tempo.now
            val peakCount = getTenMinutePhase.invoke()
            val tonicAvg = getTenMinuteTonic.invoke()
            if (getTenMinuteResultCount.invoke() == 0) {
                val result = ResultDomainModel(
                    time,
                    peakCount,
                    tonicAvg,
                    1
                )
                writeResult.invoke(result)
                deviceBleWriter.recordAll(peakCount, tonicAvg, time)
                if(result.peakCount > Singleton.PEAKS_LIMIT){
                    notificationBuilderApp.buildWarningNotification(time)
                }
            }
        }
    }

}