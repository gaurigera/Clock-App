package com.example.alarmclock.alarmUtility

import com.example.alarmclock.data.Alarm
import java.lang.StringBuilder
import java.util.Calendar

class DayUtil {
    fun setDayOrDays(alarm: Alarm): String {
        val days = StringBuilder().apply {
            if (alarm.mon) append("M ")
            if (alarm.tue) append("T ")
            if (alarm.wed) append("W ")
            if (alarm.thurs) append("Th ")
            if (alarm.fri) append("F ")
            if (alarm.sat) append("S ")
            if (alarm.sun) append("S ")
        }.toString()
        if (!days.isNullOrBlank())
            return days

        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)

        return "${getDayOfWeek(tomorrow)}, ${tomorrow.get(Calendar.DAY_OF_MONTH)}"
    }

    fun getDayOfWeek(calendar: Calendar): String {
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> throw IllegalArgumentException("Impossible day!")
        }
    }
}