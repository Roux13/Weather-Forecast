package ru.nehodov.weatherforecast.network

import ru.nehodov.weatherforecast.BuildConfig

object NetworkContract {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val WEATHER_ICONS_URL = "https://openweathermap.org/img/wn/%s@2x.png"
    const val WEATHER_API_KEY = BuildConfig.API_KEY
}