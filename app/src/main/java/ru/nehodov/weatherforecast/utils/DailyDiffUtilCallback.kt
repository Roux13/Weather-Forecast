package ru.nehodov.weatherforecast.utils

import androidx.recyclerview.widget.DiffUtil
import ru.nehodov.weatherforecast.entities.Daily

class DailyDiffUtilCallback(
    private val oldList: List<Daily>,
    private val newList: List<Daily>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDaily = oldList[oldItemPosition]
        val newDaily = newList.get(newItemPosition)
        return oldDaily.dateTime == newDaily.dateTime &&
                oldDaily.weather[0].icon == newDaily.weather.get(0).icon &&
                oldDaily.temp == newDaily.temp
    }
}