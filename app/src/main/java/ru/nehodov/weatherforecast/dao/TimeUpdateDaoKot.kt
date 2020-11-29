package ru.nehodov.weatherforecast.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.nehodov.weatherforecast.entities.TimeUpdate

@Dao
interface TimeUpdateDaoKot {

    @Insert
    fun insert(timeUpdate: TimeUpdate?)

    @Query("SELECT time FROM TimeUpdate")
    fun timeUpdateData(): LiveData<String?>

    @Query("DELETE FROM TimeUpdate")
    fun deleteAll()

}