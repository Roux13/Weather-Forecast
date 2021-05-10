package ru.nehodov.weatherforecast.entities

import com.google.gson.annotations.SerializedName

data class Weather(
    val icon: String = "",
    val description: String = "",
    val main: String = "",
    @SerializedName("id") val apiId: String = "",
)