package com.example.alarmclock.data

class AlarmRepository private constructor(private val alarmDao : FakeAlarmDAO){
    fun addAlarm (alarm : Alarm) {
        alarmDao.addAlarm(alarm)
    }
    fun getAlarm() = alarmDao.getAlarm()

    companion object {
        @Volatile private var instance: AlarmRepository?= null

        fun getInstance(alarmDao: FakeAlarmDAO) : AlarmRepository {
            return instance ?: synchronized(this) {
                instance ?: AlarmRepository(alarmDao).also { instance = it }
            }
        }
    }
}