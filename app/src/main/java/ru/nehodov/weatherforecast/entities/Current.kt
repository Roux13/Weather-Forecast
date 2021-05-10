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
    @TypeConverters(WeatherArrayConverter::class) val weather: Array<Weather> = emptyArray(),
    val humidity: String = "",
    @SerializedName("wind_speed") val windSpeed: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Current

        if (id != other.id) return false
        if (sunrise != other.sunrise) return false
        if (temp != other.temp) return false
        if (visibility != other.visibility) return false
        if (uvi != other.uvi) return false
        if (pressure != other.pressure) return false
        if (clouds != other.clouds) return false
        if (dateTime != other.dateTime) return false
        if (sunset != other.sunset) return false
        if (feelsLike != other.feelsLike) return false
        if (!weather.contentEquals(other.weather)) return false
        if (humidity != other.humidity) return false
        if (windSpeed != other.windSpeed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + sunrise.hashCode()
        result = 31 * result + temp.hashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + uvi.hashCode()
        result = 31 * result + pressure.hashCode()
        result = 31 * result + clouds.hashCode()
        result = 31 * result + dateTime.hashCode()
        result = 31 * result + sunset.hashCode()
        result = 31 * result + feelsLike.hashCode()
        result = 31 * result + weather.contentHashCode()
        result = 31 * result + humidity.hashCode()
        result = 31 * result + windSpeed.hashCode()
        return result
    }

}