package com.example.alarmclock.AlarmUtility

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Stopwatch

class AlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(
                StopwatchNotificationService.COUNTER_CHANNEL_ID,
                "Stopwatch",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used to toggle the stopwatch"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}