package ru.nehodov.weatherforecast.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Daily

@Dao
interface DailyDaoKot {

    @Insert
    fun insert(daily: Array<Daily>?)

    @Query("SELECT * FROM daily")
    fun dailyForecastData(): LiveData<MutableList<Daily?>?>

    @Query("DELETE FROM daily")
    fun deleteAll()

}