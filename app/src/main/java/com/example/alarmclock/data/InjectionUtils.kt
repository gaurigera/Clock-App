package com.example.alarmclock.data

object InjectionUtils {
    fun provideAlarmViewModel() : AlarmViewModelFactory {
        val alarmRepository = AlarmRepository.getInstance(AlarmDatabase.getInstance().alarmDAO)
        return AlarmViewModelFactory(alarmRepository)
    }
}