package com.example.alarmclock.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeAlarmDAO {
    private val alarmList = mutableListOf<Alarm>()
    private val alarms = MutableLiveData<List<Alarm>>()
    init {
        alarms.value = alarmList
    }
    fun addAlarm(alarm : Alarm) {
        alarmList.add(alarm)
        //Update in Database
        alarms.value = alarmList
    }
    fun getAlarm() = alarms as LiveData<List<Alarm>>
}