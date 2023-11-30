package com.example.alarmclock.ui.AlarmHome

import androidx.lifecycle.ViewModel
import com.example.alarmclock.data.Alarm
import com.example.alarmclock.data.AlarmRepository

class HomeViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {
    fun getAlarms() = alarmRepository.getAlarm()
    fun addAlarm(alarm: Alarm) = alarmRepository.addAlarm(alarm)
}