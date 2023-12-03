package com.example.alarmclock.helpers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarmclock.alarmUtility.Constants.CANCEL_REQUEST_CODE
import com.example.alarmclock.alarmUtility.Constants.CLICK_REQUEST_CODE
import com.example.alarmclock.alarmUtility.Constants.RESUME_REQUEST_CODE
import com.example.alarmclock.alarmUtility.Constants.STOPWATCH_STATE
import com.example.alarmclock.alarmUtility.Constants.STOP_REQUEST_CODE
import com.example.alarmclock.alarmUtility.StopwatchState
import com.example.alarmclock.services.StopwatchService
import com.example.alarmclock.ui.Stopwatch.StopwatchFragment

object SwServiceHelper {
    private const val flag = PendingIntent.FLAG_IMMUTABLE
    fun clickPendingIntent(context: Context): PendingIntent {
        val clickIntent = Intent(context, StopwatchFragment::class.java)
        return PendingIntent.getActivity(
            context, CLICK_REQUEST_CODE, clickIntent, flag
        )
    }
    fun stopPendingIntent(context: Context): PendingIntent {
        val stopIntent = Intent(context, StopwatchService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.Paused.name)
        }
        return PendingIntent.getService(
            context, STOP_REQUEST_CODE, stopIntent, flag
        )
    }
    fun resumePendingIntent(context: Context): PendingIntent {
        val resumeIntent = Intent(context, StopwatchService::class.java)
            .apply {
                putExtra(STOPWATCH_STATE, StopwatchState.Started.name)
            }
        return PendingIntent.getService(
            context, RESUME_REQUEST_CODE, resumeIntent, flag
        )
    }
    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, StopwatchService::class.java)
            .apply {
                putExtra(STOPWATCH_STATE, StopwatchState.Stopped.name)
            }
        return PendingIntent.getService(
            context, CANCEL_REQUEST_CODE, cancelIntent, flag
        )
    }
    fun triggerForegroundService(context: Context, action: String) {
        Intent(context, StopwatchService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
}