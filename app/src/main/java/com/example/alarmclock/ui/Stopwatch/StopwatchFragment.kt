package com.example.alarmclock.ui.Stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.databinding.FragmentStopwatchBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Stopwatch

class StopwatchFragment : Fragment() {

private var _binding: FragmentStopwatchBinding? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var chronometer: Chronometer
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
        ViewModelProvider(this)[StopwatchViewModel::class.java]

    _binding = FragmentStopwatchBinding.inflate(inflater, container, false)
    val root: View = binding.root

      fab = binding.floatingActionButton
      chronometer = binding.mainTimer

    return root
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isRunning = false
        var elapsedTime: Long = 0

        fab.setOnClickListener {
            if (isRunning) {
                // Stop the chronometer
                chronometer.stop()
                // Save the elapsed time when stopped
                elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
                // Change FAB icon to play
                fab.setImageResource(R.drawable.baseline_play_arrow_24)
            } else {
                // Start the chronometer
                chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
                chronometer.start()
                // Change FAB icon to pause
                fab.setImageResource(R.drawable.baseline_pause_24)
            }

            // Toggle the running state
            isRunning = !isRunning
        }

    }
override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}