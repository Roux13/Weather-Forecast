package ru.nehodov.weatherforecast.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Daily

@Dao
interface CurrentDaoKot {

    @Insert
    fun insert(daily: Daily?)

    @Query("SELECT * FROM current")
    fun currentWeatherData(): LiveData<Daily?>

    @Query("DELETE FROM current")
    fun deleteAll()
}