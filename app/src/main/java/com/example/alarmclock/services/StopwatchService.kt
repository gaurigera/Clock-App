package com.example.alarmclock.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.alarmclock.alarmUtility.Constants.ACTION_SERVICE_PAUSE
import com.example.alarmclock.alarmUtility.Constants.ACTION_SERVICE_START
import com.example.alarmclock.alarmUtility.Constants.ACTION_SERVICE_STOP
import com.example.alarmclock.alarmUtility.Constants.STOPWATCH_CHANNEL_ID
import com.example.alarmclock.alarmUtility.Constants.STOPWATCH_CHANNEL_NAME
import com.example.alarmclock.alarmUtility.Constants.STOPWATCH_NOTIFICATION_ID
import com.example.alarmclock.alarmUtility.Constants.STOPWATCH_STATE
import com.example.alarmclock.alarmUtility.StopwatchState
import com.example.alarmclock.helpers.SwServiceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StopwatchService : Service() {
    private val binder: StopwatchBinder = StopwatchBinder()
    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    override fun onBind(intent: Intent?) = binder

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.Started.name -> {
                setStopButton()
                startForegroundNotificationService()
            }

            StopwatchState.Paused.name -> {
                setResumeButton()
            }

            StopwatchState.Stopped.name -> {
                stopForegroundNotificationService()
            }
        }
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_STOP -> {
                    stopForegroundNotificationService()
                }

                ACTION_SERVICE_PAUSE -> {

                }

                ACTION_SERVICE_START -> {
                    startForegroundNotificationService()
                }
            }
        }
        return START_NOT_STICKY
    }
    private fun setStopButton() {
        notificationBuilder.clearActions()
        notificationBuilder
            .addAction(0, "PAUSE", SwServiceHelper.stopPendingIntent(this))
        notificationBuilder
            .addAction(0, "STOP", SwServiceHelper.cancelPendingIntent(this))
    }
    private fun setResumeButton() {
        notificationBuilder.clearActions()
        notificationBuilder
            .addAction(0, "RESUME", SwServiceHelper.resumePendingIntent(this))
        notificationBuilder
            .addAction(0, "STOP", SwServiceHelper.cancelPendingIntent(this))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun startForegroundNotificationService() {
        createNotificationChannel()
        startForeground(STOPWATCH_NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun stopForegroundNotificationService() {
        notificationManager.cancel(STOPWATCH_NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                STOPWATCH_CHANNEL_ID,
                STOPWATCH_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun updateNotification(counter: String) {
        notificationManager.notify(
            STOPWATCH_NOTIFICATION_ID,
            notificationBuilder.setContentText(
                counter
            ).build()
        )
    }

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }
}