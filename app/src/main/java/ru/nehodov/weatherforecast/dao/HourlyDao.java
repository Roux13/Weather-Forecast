package ru.nehodov.weatherforecast.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.nehodov.weatherforecast.entities.Hourly;

@Dao
public interface HourlyDao {

    @Insert
    void insertHourlyForecast(Hourly[] hourlies);

    @Query("SELECT * FROM hourly")
    LiveData<List<Hourly>> getHourlyForecast();

    @Query("DELETE FROM hourly")
    void deleteHourlyForecast();
}
