package ru.nehodov.weatherforecast.utils

import ru.nehodov.weatherforecast.entities.Current
import ru.nehodov.weatherforecast.entities.Daily
import ru.nehodov.weatherforecast.entities.FeelsLike
import ru.nehodov.weatherforecast.entities.Temp

object CurrentToDailyConverter {

    fun convert(current: Current): Daily {
        val temp = Temp(current.temp, current.temp)
        val feelsLike = FeelsLike(current.feelsLike, current.feelsLike)
        return Daily(
            current.id,
            current.sunrise,
            temp,
            feelsLike,
            current.uvi,
            current.pressure,
            current.clouds,
            current.dateTime,
            current.sunset,
            current.weather,
            current.humidity,
            current.windSpeed
        )
    }
}