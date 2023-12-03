package com.example.alarmclock.ui.AlarmHome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.data.Alarm
import com.example.alarmclock.databinding.SingleAlarmBinding
import com.example.alarmclock.alarmUtility.DayUtil

class HomeRecyclerViewAdapter(var alarms: MutableList<Alarm>)
    : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    lateinit var dayUtil : DayUtil
    class ViewHolder(val binding : SingleAlarmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SingleAlarmBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Implement turning the alarm off on an through the toggle button
        holder.binding.apply {
            val time = "${alarms[position].hour}:${alarms[position].min}"
            alarmTime.text = time
            dayUtil = DayUtil()
            daysDate.text = dayUtil.setDayOrDays(alarms[position])
            toggleButton.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun setAlarm(alarms : List<Alarm>) {
        this.alarms = alarms.toMutableList()
        notifyDataSetChanged()
    }
}