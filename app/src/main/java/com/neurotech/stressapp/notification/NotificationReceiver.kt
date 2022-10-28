package com.neurotech.stressapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.cesarferreira.tempo.toDate
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.usecases.resultdata.SetStressCauseByTime
import com.neurotech.stressapp.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var setStressCauseByTime: SetStressCauseByTime

    companion object {
        const val SOURCE_EXTRA = "com.neurotech.stressapp.notification.SOURCE_EXTRA"
        const val TIME_EXTRA = "com.neurotech.stressapp.notification.TIME_EXTRA"
        const val FIRST_SOURCE_ACTION = "com.neurotech.stressapp.notification.FIRST_SOURCE_ACTION"
        const val SECOND_SOURCE_ACTION = "com.neurotech.stressapp.notification.SECOND_SOURCE_ACTION"
    }


    override fun onReceive(context: Context, intent: Intent) {
        (context.applicationContext as App).component.inject(this)
        if (intent.action in listOf(FIRST_SOURCE_ACTION, SECOND_SOURCE_ACTION)) {
            CoroutineScope(Dispatchers.IO).launch {
                val source = intent.getStringExtra(SOURCE_EXTRA)
                val time = intent.getStringExtra(TIME_EXTRA)
                if (source != null && time != null) {
                    setStressCauseByTime
                        .invoke(
                            source,
                            listOf(time.toDate(TimeFormat.dateTimeFormatDataBase))
                        )
                }
            }
        }
        NotificationManagerCompat.from(context).apply {
            cancel(3)
        }
    }
}