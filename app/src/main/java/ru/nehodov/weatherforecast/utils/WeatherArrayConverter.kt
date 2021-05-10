package ru.nehodov.weatherforecast.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.nehodov.weatherforecast.entities.Weather

class WeatherArrayConverter {
    @TypeConverter
    fun fromList(weathers: List<Weather>): String {
        val gson = Gson()
        return gson.toJson(weathers)
    }

    @TypeConverter
    fun fromString(value: String): List<Weather> {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(value, type)
    }
}