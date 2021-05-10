package ru.nehodov.weatherforecast.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.nehodov.weatherforecast.utils.WeatherArrayConverter

@Entity(tableName = "current")
data class Current(
    @PrimaryKey(autoGenerate = true) @SerializedName("room_id") val id: Long = 0L,
    val sunrise: String = "",
    val temp: String = "",
    val visibility: String = "",
    val uvi: String = "",
    val pressure: String = "",
    val clouds: String = "",
    @SerializedName("dt") val dateTime: String = "",
    val sunset: String = "",
    @SerializedName("feels_like") val feelsLike: String = "",
    @TypeConverters(WeatherArrayConverter::class) val weather: List<Weather> = emptyList(),
    val humidity: String = "",
    @SerializedName("wind_speed") val windSpeed: String = "",
)