package ru.nehodov.weatherforecast.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.nehodov.weatherforecast.entities.Daily;

@Dao
public interface DailyDao {

    @Insert
    void insertDailyForecast(Daily[] dailies);

    @Query("SELECT * FROM daily")
    LiveData<List<Daily>> getDailyForecast();

    @Query("DELETE FROM daily")
    void deleteDailyForecast();
}
