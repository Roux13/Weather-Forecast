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
    @TypeConverters(WeatherArrayConverter::class) val weather: Array<Weather> = emptyArray(),
    val clouds: String = "",
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hourly

        if (id != other.id) return false
        if (dateTime != other.dateTime) return false
        if (pop != other.pop) return false
        if (temp != other.temp) return false
        if (!weather.contentEquals(other.weather)) return false
        if (clouds != other.clouds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dateTime.hashCode()
        result = 31 * result + pop.hashCode()
        result = 31 * result + temp.hashCode()
        result = 31 * result + weather.contentHashCode()
        result = 31 * result + clouds.hashCode()
        return result
    }
}