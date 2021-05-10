package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.CurrentLocation

@Dao
interface CurrentLocationDao {

    @Insert
    fun insert(currentLocation: CurrentLocation)

    @Query("SELECT * FROM CurrentLocation")
    fun currentLocationData(): CurrentLocation

    @Query("DELETE FROM CurrentLocation")
    fun deleteAll()
}