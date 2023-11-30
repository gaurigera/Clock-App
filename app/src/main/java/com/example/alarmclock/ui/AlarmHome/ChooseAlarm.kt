package com.example.alarmclock.ui.AlarmHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alarmclock.R
import com.example.alarmclock.data.Alarm
import com.example.alarmclock.data.InjectionUtils
import com.example.alarmclock.databinding.FragmentChooseAlarmBinding

class ChooseAlarm : Fragment() {
    private lateinit var binding: FragmentChooseAlarmBinding
    private lateinit var homeViewModel: HomeViewModel
//    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_ca_to_home_fragment)
        }
        binding.saveButton.setOnClickListener {
            val alarm = Alarm(
                binding.alarmName.text.toString(), binding.timePicker.hour, binding.timePicker.minute, binding.toggleVibration.isChecked,
                binding.soundName.text.toString(), binding.Mon.isChecked, binding.Tue.isChecked,
                binding.Wed.isChecked, binding.Thurs.isChecked, binding.Fri.isChecked,
                binding.Sat.isChecked, binding.Sun.isChecked
            )
            val factory = InjectionUtils.provideAlarmViewModel()
            homeViewModel = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)

           homeViewModel.addAlarm(alarm)
            Toast.makeText(context, "Alarm Saved!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(ChooseAlarmDirections.actionCaToHomeFragment())
        }
    }
}