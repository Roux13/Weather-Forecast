package ru.nehodov.weatherforecast.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import ru.nehodov.weatherforecast.utils.WeatherArrayConverter

@Entity(tableName = "Hourly")
data class Hourly(
    @PrimaryKey(autoGenerate = true) @SerializedName("room_id") val id: Long = 0,
    @SerializedName("dt") val dateTime: String = "",
    val pop: String = "",
    val temp: String = "",
    @TypeConverters(WeatherArrayConverter::class) val weather: List<Weather> = emptyList(),
    val clouds: String = "",
)