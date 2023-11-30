package com.example.alarmclock.ui.AlarmHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.data.InjectionUtils
import com.example.alarmclock.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
//    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = InjectionUtils.provideAlarmViewModel()
        val homeViewModel = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)

        val adapter = HomeRecyclerViewAdapter(homeViewModel.getAlarms().value.orEmpty().toMutableList())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        homeViewModel.getAlarms().observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
            if (adapter.itemCount == 0)
                binding.numberOfAlarms.text = "Click on + to set an alarm"
            if(adapter.itemCount == 1)
            binding.numberOfAlarms.text = "${adapter.itemCount} alarm set"
            if (adapter.itemCount > 1)
                binding.numberOfAlarms.text = "${adapter.itemCount} alarms set"
        })

        binding.addAlarm.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToCaFragment()
            navController.navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}