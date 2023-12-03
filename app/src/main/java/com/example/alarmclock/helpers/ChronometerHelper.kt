package com.example.alarmclock.helpers

import android.os.SystemClock
import android.widget.Chronometer

class ChronometerHelper(private val chronometer: Chronometer) {
    var isRunning = false
    private var pauseOffset = 0L

    fun startChronometer() {
        if (!isRunning) {
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            isRunning = true
        }
    }

    fun stopChronometer() {
        if (isRunning) {
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            chronometer.stop()
            isRunning = false
        }
    }

    fun getElapsedTime(): String {
        return chronometer.text.toString()
    }

    fun resetChronometer() {
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0L
    }
}