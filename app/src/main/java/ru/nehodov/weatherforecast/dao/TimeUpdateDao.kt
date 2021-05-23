package ru.nehodov.weatherforecast.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nehodov.weatherforecast.entities.TimeUpdate

@Dao
interface TimeUpdateDao {

    @Insert
    suspend fun insert(timeUpdate: TimeUpdate)

    @Query("SELECT time FROM TimeUpdate")
    fun timeUpdateData(): Flow<String>

    @Query("DELETE FROM TimeUpdate")
    suspend fun deleteAll()

}