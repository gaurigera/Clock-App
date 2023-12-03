package com.example.alarmclock.alarmUtility

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.example.alarmclock.alarmUtility.Constants.STOPWATCH_CHANNEL_ID
import com.example.alarmclock.R
import com.example.alarmclock.helpers.SwServiceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object StopwatchNotificationModule {
    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, STOPWATCH_CHANNEL_ID)
            .setContentTitle("Stopwatch")
            .setContentText("00:00")
            .setVisibility(VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .addAction(0, "PAUSE", SwServiceHelper.stopPendingIntent(context))
            .addAction(0, "STOP", SwServiceHelper.stopPendingIntent(context))
            .setContentIntent(SwServiceHelper.clickPendingIntent(context))
            .setOngoing(true)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
    }
    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
    }
}