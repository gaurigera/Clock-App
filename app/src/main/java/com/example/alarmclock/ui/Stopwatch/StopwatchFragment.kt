package com.example.alarmclock.ui.Stopwatch

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.alarmUtility.Constants
import com.example.alarmclock.alarmUtility.Constants.ACTION_SERVICE_START
import com.example.alarmclock.databinding.FragmentStopwatchBinding
import com.example.alarmclock.helpers.ChronometerHelper
import com.example.alarmclock.helpers.SwServiceHelper
import com.example.alarmclock.services.StopwatchService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchFragment : Fragment() {

    private var _binding: FragmentStopwatchBinding? = null
    private lateinit var resetFab: FloatingActionButton
    private lateinit var fab: FloatingActionButton
    private lateinit var chronometer: Chronometer
    private lateinit var stopwatchService: StopwatchService
    private val binding get() = _binding!!
    private lateinit var chronometerHelper: ChronometerHelper
    private lateinit var viewModel: StopwatchViewModel
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as StopwatchService.StopwatchBinder
            stopwatchService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.bindService(
            Intent(activity, StopwatchService::class.java),
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this).get(StopwatchViewModel::class.java)
        fab = binding.floatingActionButton
        resetFab = binding.resetFloatingActionButton
        chronometer = binding.mainTimer
        chronometerHelper = ChronometerHelper(chronometer)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isRunning = false
//        val serviceIntent = Intent(context, StopwatchService::class.java)
//        context?.startService(serviceIntent)

        fab.setOnClickListener {
            if (isRunning) {
                stopChronometer()
            } else {
                startChronometer()
            }
            isRunning = !isRunning
        }
    }

    private fun stopChronometer() {
        chronometerHelper.stopChronometer()

        resetFab.visibility = View.VISIBLE

        resetFab.setOnClickListener {
            chronometerHelper.resetChronometer()
        }
        fab.setImageResource(R.drawable.baseline_play_arrow_24)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun startChronometer() {
        chronometerHelper.startChronometer()

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            var previousElapsedTime = ""
            while (chronometerHelper.isRunning) {
                val currentElapsedTime = chronometerHelper.getElapsedTime()
                if (currentElapsedTime != previousElapsedTime) {
                    stopwatchService.updateNotification(currentElapsedTime)
                    previousElapsedTime = currentElapsedTime
                }
                delay(1000)
            }
        }

        if (context?.let { it1 ->
                ContextCompat.checkSelfPermission(
                    it1,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
            != PackageManager.PERMISSION_GRANTED
            ||
            context?.let { it1 ->
                ContextCompat.checkSelfPermission(
                    it1,
                    Manifest.permission.FOREGROUND_SERVICE
                )
            }
            != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let { it1 ->
                ActivityCompat.requestPermissions(
                    it1,
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.FOREGROUND_SERVICE
                    ),
                    Constants.REQUEST_PERMISSIONS_CODE
                )
            }
        } else {
            context?.let { it1 ->
                SwServiceHelper.triggerForegroundService(
                    it1,
                    ACTION_SERVICE_START
                )
            }
        }
        resetFab.visibility = View.INVISIBLE
        fab.setImageResource(R.drawable.baseline_pause_24)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}