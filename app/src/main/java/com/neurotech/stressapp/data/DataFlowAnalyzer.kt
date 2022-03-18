package com.neurotech.stressapp.data

import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.seconds
import com.cesarferreira.tempo.toString
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.database.AppDatabase
import com.neurotech.stressapp.data.database.entity.PeakEntity
import com.neurotech.stressapp.data.database.entity.ResultEntity
import com.neurotech.stressapp.data.database.entity.TonicEntity
import com.neurotech.stressapp.data.spsettings.SettingsApi
import java.util.*
import javax.inject.Inject

class DataFlowAnalyzer {

    @Inject
    lateinit var dataBase: AppDatabase
    private var lastInsertDatabase = Tempo.now
    private val settingsApi = SettingsApi()
    private val timeFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    private var isPeak = false

    private var beginPeak: Date? = null
    private var endPeak: Date? = null
    private var maxValue: Double = 0.0


    init {
        Singleton.daggerComponent.inject(this)
    }

    fun putTonicFlow(tonicItem: HashMap<String,Any>){
        if(Tempo.now-30.seconds>lastInsertDatabase){
            lastInsertDatabase = Tempo.now
            val time = (tonicItem["time"] as Date).toString(timeFormat)
            val value = tonicItem["value"] as Int
            dataBase.tonicDao().insertTonicValue(TonicEntity(time,value))
        }
    }

    fun putPhaseFlow(phaseItem:HashMap<String, Any>){
        val time = (phaseItem["time"] as Date)
        val value = phaseItem["value"] as Double
        if (value > settingsApi.getThreshold() && !isPeak){
            beginPeak = time
            isPeak = true
        }
        if (value < settingsApi.getThreshold() && isPeak){
            endPeak = time
            dataBase.peakDao().insertPeak(PeakEntity(
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
        if(dataBase.resultDao().getTenMinuteCount() == 0){
            dataBase.resultDao().insertResult(
                ResultEntity(
                    Tempo.now.toString(timeFormat),
                    dataBase.peakDao().getTenMinuteCount(),
                    dataBase.tonicDao().getTenMinuteAvg(),
                    1
                )
            )
        }
    }
}