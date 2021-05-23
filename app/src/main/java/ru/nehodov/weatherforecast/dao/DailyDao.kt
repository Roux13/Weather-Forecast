package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nehodov.weatherforecast.entities.Daily

@Dao
interface DailyDao {

    @Insert
    suspend fun insert(daily: List<Daily>)

    @Query("SELECT * FROM daily")
    fun dailyForecastData(): Flow<List<Daily>>

    @Query("DELETE FROM daily")
    suspend fun deleteAll()

}