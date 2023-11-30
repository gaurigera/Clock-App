package com.example.alarmclock.AlarmUtility

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import java.sql.Time

class StopwatchNotificationService(
    private val context: Context
) {

    fun showNotification (time: Time) {
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setContentTitle("Stopwatch")
        
    }
    companion object{
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}