package ru.nehodov.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nehodov.weatherforecast.R
import ru.nehodov.weatherforecast.SelectedDayListener
import ru.nehodov.weatherforecast.databinding.DailyItemBinding
import ru.nehodov.weatherforecast.entities.Daily
import ru.nehodov.weatherforecast.utils.WeatherUtil

class DailyForecastAdapter(private val selectedDayListener: SelectedDayListener) :
    RecyclerView.Adapter<DailyForecastAdapter.DailyForecastHolder>() {

    private val dailyForecasts: MutableList<Daily> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: DailyItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.daily_item, parent, false)
        return DailyForecastHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastHolder, position: Int) {
        val daily = dailyForecasts[position]
        holder.itemView.setOnClickListener { selectedDayListener.selectDay(position) }
        holder.bind(daily)
    }

    override fun getItemCount(): Int {
        return dailyForecasts.size
    }

    fun setDailyForecasts(dailyForecasts: List<Daily>) {
        this.dailyForecasts.apply {
            clear()
            addAll(dailyForecasts)
        }
    }

    fun getDailyForecasts(): List<Daily> {
        return this.dailyForecasts
    }

    class DailyForecastHolder(private val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(daily: Daily?) {
            binding.apply {
                dailyViewData = DailyViewData(
                    dateTime = WeatherUtil.formatDate(daily?.dateTime ?: ""),
                    maxTemp = WeatherUtil.formatTemp(daily?.temp?.max ?: ""),
                    minTemp = WeatherUtil.formatTemp(daily?.temp?.min ?: ""),
                    iconPath = daily?.weather?.get(0)?.icon ?: ""
                )

                executePendingBindings()
            }
        }

    }

    data class DailyViewData(
        val dateTime: String,
        val maxTemp: String,
        val minTemp: String,
        val iconPath: String
    )

}