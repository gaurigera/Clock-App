package com.example.alarmclock.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.ui.AlarmHome.HomeViewModel

class AlarmViewModelFactory(private val alarmRepository: AlarmRepository)
        : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(alarmRepository) as T
        }
    }
