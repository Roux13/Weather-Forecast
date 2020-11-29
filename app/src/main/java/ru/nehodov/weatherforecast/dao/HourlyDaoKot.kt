package ru.nehodov.weatherforecast.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.Hourly

@Dao
interface HourlyDaoKot {

    @Insert
    fun insert(hourlies: Array<Hourly?>?)

    @Query("SELECT * FROM hourly")
    fun hourlyData(): LiveData<MutableList<Hourly?>?>

    @Query("DELETE FROM hourly")
    fun deleteAll()

}