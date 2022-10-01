package com.neurotech.stressapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cesarferreira.tempo.toDate
import com.cesarferreira.tempo.toString
import com.neurotech.data.R
import com.neurotech.domain.models.MarkupDomainModel
import com.neurotech.domain.usecases.markupdata.GetMarkupList
import com.neurotech.stressapp.App
import com.neurotech.stressapp.ui.MainActivity
import java.util.*
import javax.inject.Inject


class NotificationBuilderApp(private val context: Context) {

    @Inject
    lateinit var getMarkups: GetMarkupList

    companion object {
        const val FOREGROUND_CHANNEL_ID = "foreground_channel"
        const val WARNING_CHANNEL_ID = "warning_channel"
    }

    private val titleNotification = "StressApp"
    private val foregroundContent = "Будьте спокойны..."
    private val warningContent = "Был обнаружен повышенный стресс"

    private val dateTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    private val timeFormat = "HH:mm"


    fun buildForegroundNotification(): Notification {
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    context.applicationContext,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        val notification: Notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context.applicationContext, FOREGROUND_CHANNEL_ID)
                    .setContentTitle(titleNotification)
                    .setContentText(foregroundContent)
                    .setSmallIcon(R.drawable.icon_stress)
                    .setContentIntent(pendingIntent)
                    .setTicker(titleNotification)
                    .build()
            } else {
                NotificationCompat.Builder(context)
                    .setContentTitle(titleNotification)
                    .setContentText(foregroundContent)
                    .setSmallIcon(R.drawable.icon_stress)
                    .setContentIntent(pendingIntent)
                    .setTicker(titleNotification)
                    .build()
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                FOREGROUND_CHANNEL_ID,
                "ForegroundChannel",
                "ForegroundService"
            )
        }

        return notification
    }

    private suspend fun getMarkupOutOfRange(time: String): MarkupDomainModel? {
        (context as App).component.inject(this)
        val markups = getMarkups.invoke()
        var resultMarkup: MarkupDomainModel? = null
        for (markup in markups) {
            if (time.toDate(timeFormat) in
                markup.timeBegin!!.toDate(timeFormat)..markup.timeEnd!!.toDate(timeFormat)
            ) {
                resultMarkup = markup
            }
        }
        return resultMarkup
    }

    private suspend fun getPendingIntent(time: Date): Map<String,PendingIntent>{
        val markup = getMarkupOutOfRange(time.toString(timeFormat))
        if(markup?.firstSource != null && markup.secondSource != null){
            val firstSourcePendingIntent: PendingIntent =
                Intent(context, NotificationReceiver::class.java).let {
                    it.action = NotificationReceiver.FIRST_SOURCE_ACTION
                    it.putExtra(NotificationReceiver.SOURCE_EXTRA, markup.firstSource)
                    it.putExtra(NotificationReceiver.TIME_EXTRA, time.toString(dateTimeFormat))
                    PendingIntent.getBroadcast(
                        context,
                        0,
                        it,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

            val secondSourcePendingIntent: PendingIntent =
                Intent(context, NotificationReceiver::class.java).let {
                    it.action = NotificationReceiver.SECOND_SOURCE_ACTION
                    it.putExtra(NotificationReceiver.SOURCE_EXTRA, markup.secondSource)
                    it.putExtra(NotificationReceiver.TIME_EXTRA, time.toString(dateTimeFormat))
                    PendingIntent.getBroadcast(
                        context,
                        0,
                        it,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
            return mapOf(markup.firstSource!! to firstSourcePendingIntent, markup.secondSource!! to secondSourcePendingIntent)
        }
        return mapOf()
    }

    suspend fun buildWarningNotification(time: Date) {
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    context.applicationContext,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }

        val builder = NotificationCompat.Builder(context, WARNING_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_stress)
            .setContentTitle(titleNotification)
            .setContentText(warningContent)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        getPendingIntent(time).forEach {
            builder.addAction(R.drawable.icon_stress, it.key , it.value)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(WARNING_CHANNEL_ID, WARNING_CHANNEL_ID, WARNING_CHANNEL_ID)
        }

        with(NotificationManagerCompat.from(context)) {
            cancelAll()
            notify(3, builder.build())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        name: String,
        descriptionText: String
    ) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}