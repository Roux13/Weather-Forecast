package ru.nehodov.weatherforecast.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import ru.nehodov.weatherforecast.entities.UpdateTime;

@Dao
public interface UpdateTimeDao {

    @Insert
    void insertUpdateTime(UpdateTime updateTime);

    @Query("SELECT time FROM updatetime")
    LiveData<String> getUpdateTime();

    @Query("DELETE FROM updatetime")
    void deleteAll();

}
