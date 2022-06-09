package com.neurotech.stressapp

import android.annotation.SuppressLint
import android.content.Context
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toString
import com.neurotech.domain.TimeFormat
import java.io.File

@SuppressLint("StaticFieldLeak")
object Singleton {
    const val DEBUG = false
    const val TEN_MINUTE = "10М"
    const val HOUR = "1Ч"
    const val DAY = "1Д"
    const val PEAKS_LIMIT = 15

    /////????////////
    var recoding = false
    lateinit var file: File
    lateinit var context: Context

    fun startRecoding() {
        if (!recoding) {
            val path = context.getExternalFilesDir("files")
            file = File(path, Tempo.now.toString(TimeFormat.dateTimeFormatDataBase) + ".txt")
            file.createNewFile()
            recoding = true
        }
    }


    fun stopRecoding() {
        if (recoding) {
            recoding = false
        }
    }


}