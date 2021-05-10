package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Daily

@Dao
interface DailyDao {

    @Insert
    suspend fun insert(daily: List<Daily>)

    @Query("SELECT * FROM daily")
    suspend fun dailyForecastData(): List<Daily>

    @Query("DELETE FROM daily")
    suspend fun deleteAll()

}