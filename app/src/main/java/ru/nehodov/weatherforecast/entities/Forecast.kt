package ru.nehodov.weatherforecast.entities

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("current") val current: Current = Current(),
    val timezone: String = "",
    @SerializedName("lat") val latitude: Double = 0.0,
    @SerializedName("lon") val longitude: Double = 0.0,
    val daily: List<Daily> = emptyList(),
    val hourly: List<Hourly> = emptyList(),
)