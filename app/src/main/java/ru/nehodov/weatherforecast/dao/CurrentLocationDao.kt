package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.CurrentLocation

@Dao
interface CurrentLocationDao {

    @Insert
    suspend fun insert(currentLocation: CurrentLocation)

    @Query("SELECT * FROM CurrentLocation")
    suspend fun currentLocationData(): CurrentLocation

    @Query("DELETE FROM CurrentLocation")
    suspend fun deleteAll()
}