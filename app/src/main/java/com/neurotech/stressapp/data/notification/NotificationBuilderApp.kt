package com.neurotech.stressapp.data.notification

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
import com.neurotech.stressapp.R
import com.neurotech.stressapp.ui.MainActivity

class NotificationBuilderApp(private val context: Context) {

    companion object{
        const val FOREGROUND_CHANNEL_ID = "foreground_channel"
    }

    private val titleNotification = "StressApp"
    private val foregroundContent = "Будте спокойны..."

    fun buildForegroundNotification():Notification{

        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(context.applicationContext, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
            }

        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context.applicationContext, FOREGROUND_CHANNEL_ID)
                .setContentTitle(titleNotification)
                .setContentText(foregroundContent)
                .setSmallIcon(R.drawable.icon_stress)
                .setContentIntent(pendingIntent)
                .setTicker(titleNotification)
                .build()
        } else {
            NotificationCompat.Builder(context, FOREGROUND_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_stress)
                .setContentTitle(titleNotification)
                .setContentText(foregroundContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(FOREGROUND_CHANNEL_ID)
        }

        return notification
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String) {
        val name = channelId
        val descriptionText = channelId
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}