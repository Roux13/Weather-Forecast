package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Current

@Dao
interface CurrentDao {

    @Insert
    fun insert(daily: Current)

    @Query("SELECT * FROM current")
    fun currentWeatherData(): Current

    @Query("DELETE FROM current")
    fun deleteAll()
}