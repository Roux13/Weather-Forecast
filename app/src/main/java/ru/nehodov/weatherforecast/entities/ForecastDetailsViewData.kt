package ru.nehodov.weatherforecast.entities

data class ForecastDetailsViewData(
    val dateTime: String = "",
    val description: String = "",
    val iconPath: String = "",
    val maxTemp: String = "",
    val feelsLike: String = "",
    val humidity: String = "",
    val uvi: String = "",
    val pressure: String = "",
    val windSpeed: String = "",
)