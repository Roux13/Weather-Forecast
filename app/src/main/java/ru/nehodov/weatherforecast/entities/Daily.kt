package ru.nehodov.weatherforecast.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.nehodov.weatherforecast.utils.WeatherArrayConverter

@Entity(tableName = "Daily")
data class Daily(
    @PrimaryKey(autoGenerate = true) @SerializedName("room_id") val id: Long = 0,
    val sunrise: String = "",
    @Embedded val temp: Temp,
    @Embedded @SerializedName("feels_like") val feelsLike: FeelsLike,
    val uvi: String = "",
    val pressure: String = "",
    val clouds: String = "",
    @SerializedName("dt") val dateTime: String = "",
    val sunset: String = "",
    @TypeConverters(WeatherArrayConverter::class) val weather: List<Weather> = emptyList(),
    val humidity: String = "",
    @SerializedName("wind_speed") val windSpeed: String = "",
)