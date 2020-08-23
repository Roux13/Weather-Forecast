package ru.nehodov.weatherforecast.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ru.nehodov.weatherforecast.entities.Current;

@Dao
public interface CurrentDao {

    @Insert
    void insertCurrentWeather(Current current);

    @Query("SELECT * FROM current")
    LiveData<Current> getCurrentWeather();

    @Query("DELETE FROM current")
    void deleteCurrentWeather();
}
