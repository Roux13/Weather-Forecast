package ru.nehodov.weatherforecast.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.CurrentLocation

@Dao
interface CurrentLocationDaoKot {

    @Insert
    fun insert(currentLocation: CurrentLocation)

    @Query("SELECT * FROM currentlocation")
    fun currentLocationData(): LiveData<CurrentLocation?>

    @Query("DELETE FROM currentlocation")
    fun deleteAll()
}