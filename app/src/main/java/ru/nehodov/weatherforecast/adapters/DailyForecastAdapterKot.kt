package ru.nehodov.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nehodov.weatherforecast.R
import ru.nehodov.weatherforecast.SelectedDayListenerKot
import ru.nehodov.weatherforecast.databinding.DailyItemBinding
import ru.nehodov.weatherforecast.entities.Daily

class DailyForecastAdapterKot(private val selectedDayListener: SelectedDayListenerKot)
    : RecyclerView.Adapter<DailyForecastAdapterKot.DailyForecastHolderKot>() {

    private val dailyForecasts: MutableList<Daily?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastHolderKot {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: DailyItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.daily_item, parent, false)
        return DailyForecastHolderKot(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastHolderKot, position: Int) {
        val daily = dailyForecasts[position]
        holder.itemView.setOnClickListener { selectedDayListener.selectDay(position) }
        holder.bind(daily)
    }

    override fun getItemCount(): Int {
        return dailyForecasts.size
    }

    fun setDailyForecasts(dailyForecasts: MutableList<Daily?>) {
        this.dailyForecasts.apply {
            clear()
            addAll(dailyForecasts)
        }
    }

    fun getDailyForecasts(): MutableList<Daily?> {
        return this.dailyForecasts
    }

    class DailyForecastHolderKot(private val binding: DailyItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(daily: Daily?) {
            binding.apply {
                setDaily(daily)
                executePendingBindings()
            }
        }

    }

}