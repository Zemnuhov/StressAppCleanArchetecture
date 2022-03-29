package com.neurotech.test.service

import android.content.Context
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.seconds
import com.cesarferreira.tempo.toString
import com.neurotech.test.App
import com.neurotech.data.modules.settings.Settings
import com.neurotech.data.modules.storage.database.dao.PeakDao
import com.neurotech.test.storage.database.dao.ResultDao
import com.neurotech.test.storage.database.dao.TonicDao
import com.neurotech.test.storage.database.entity.PeakEntity
import com.neurotech.test.storage.database.entity.ResultEntity
import com.neurotech.test.storage.database.entity.TonicEntity
import java.util.*
import javax.inject.Inject

class DataFlowAnalyzer(context: Context) {

    @Inject
    lateinit var peakDao: PeakDao
    @Inject
    lateinit var tonicDao: TonicDao
    @Inject
    lateinit var resultDao: ResultDao
    @Inject
    lateinit var settings: Settings

    private var lastInsertDatabase = Tempo.now
    private val timeFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    private var isPeak = false

    private var beginPeak: Date? = null
    private var endPeak: Date? = null
    private var maxValue: Double = 0.0


    init {
        (context as App).component.inject(this)
    }

    fun putTonicFlow(tonicItem: TonicModelService){
        if(Tempo.now-30.seconds>lastInsertDatabase){
            lastInsertDatabase = Tempo.now
            val time = tonicItem.time.toString(timeFormat)
            val value = tonicItem.value
            tonicDao.insertTonicValue(TonicEntity(time,value))
        }
    }

    fun putPhaseFlow(phaseItem:PhaseModelService){
        val time = phaseItem.time
        val value = phaseItem.value
        if (value > settings.getThreshold() && !isPeak){
            beginPeak = time
            isPeak = true
        }
        if (value < settings.getThreshold() && isPeak){
            endPeak = time
            peakDao.insertPeak(
                PeakEntity(
                beginPeak!!.toString(timeFormat),
                endPeak!!.toString(timeFormat),
                maxValue)
            )
            isPeak = false
        }
        if(isPeak){
            if(value>maxValue){
                maxValue = value
            }
        }
    }

    fun writeResultTenMinutes(){
        if(resultDao.getTenMinuteCount() == 0){
            resultDao.insertResult(
                ResultEntity(
                    Tempo.now.toString(timeFormat),
                    peakDao.getTenMinuteCount(),
                    tonicDao.getTenMinuteAvg(),
                    1
                )
            )
        }
    }
}