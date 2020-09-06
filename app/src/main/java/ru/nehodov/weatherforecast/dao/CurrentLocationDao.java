package ru.nehodov.weatherforecast.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ru.nehodov.weatherforecast.entities.CurrentLocation;

@Dao
public interface CurrentLocationDao {

    @Insert
    void insert(CurrentLocation location);

    @Query("SELECT * FROM currentlocation")
    LiveData<CurrentLocation> get();

    @Query("DELETE FROM currentlocation")
    void deleteAll();

    @Query("SELECT * FROM currentlocation")
    CurrentLocation getWithouLiveData();
}
