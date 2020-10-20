package ru.nehodov.weatherforecast.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ru.nehodov.weatherforecast.entities.Daily;

@Dao
public interface CurrentDao {

    @Insert
    void insertCurrentWeather(Daily daily);

    @Query("SELECT * FROM current")
    LiveData<Daily> getCurrentWeather();

    @Query("DELETE FROM current")
    void deleteCurrentWeather();
}
