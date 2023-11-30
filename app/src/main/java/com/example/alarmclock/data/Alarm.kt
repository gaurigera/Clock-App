package com.example.alarmclock.data

data class Alarm(
    val name : String,
    val hour : Int,
    val min : Int,
    val vibration : Boolean,
    val sound : String,
    val mon : Boolean,
    val tue : Boolean,
    val wed : Boolean,
    val thurs : Boolean,
    val fri : Boolean,
    val sat : Boolean,
    val sun : Boolean,
)
