package com.example.alarmclock.data

class AlarmDatabase private constructor() {
    var alarmDAO = FakeAlarmDAO()
        private set

    companion object {
        @Volatile private var instance: AlarmDatabase?= null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: AlarmDatabase().also { instance = it }
        }
    }
}