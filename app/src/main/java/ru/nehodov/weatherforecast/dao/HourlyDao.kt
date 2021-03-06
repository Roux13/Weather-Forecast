package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Hourly

@Dao
interface HourlyDao {

    @Insert
    suspend fun insert(hourlies: List<Hourly>)

    @Query("SELECT * FROM hourly")
    suspend fun hourlyData(): List<Hourly>

    @Query("DELETE FROM hourly")
    suspend fun deleteAll()

}