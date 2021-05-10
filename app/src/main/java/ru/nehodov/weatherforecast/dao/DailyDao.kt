package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Daily

@Dao
interface DailyDao {

    @Insert
    fun insert(daily: List<Daily>)

    @Query("SELECT * FROM daily")
    fun dailyForecastData(): List<Daily>

    @Query("DELETE FROM daily")
    fun deleteAll()

}