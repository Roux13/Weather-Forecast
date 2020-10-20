package ru.nehodov.weatherforecast.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.nehodov.weatherforecast.dao.CurrentDao;
import ru.nehodov.weatherforecast.dao.CurrentLocationDao;
import ru.nehodov.weatherforecast.dao.DailyDao;
import ru.nehodov.weatherforecast.dao.HourlyDao;
import ru.nehodov.weatherforecast.dao.UpdateTimeDao;
import ru.nehodov.weatherforecast.entities.Current;
import ru.nehodov.weatherforecast.entities.CurrentLocation;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.Hourly;
import ru.nehodov.weatherforecast.entities.UpdateTime;

@Database(entities = {
        Current.class,
        Daily.class,
        Hourly.class,
        CurrentLocation.class,
        UpdateTime.class},
        version = 3, exportSchema = false)
public abstract class ForecastDatabase extends RoomDatabase {
    public static final ExecutorService DB_EXECUTOR_SERVICE;

    private static final String DB_NAME = "forecast_db";
    private static final int NUMBER_OF_THREADS = 4;

    private static ForecastDatabase instance;

    static {
        DB_EXECUTOR_SERVICE = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public static ForecastDatabase getInstance(final Context applicationContext) {
        if (instance == null) {
            synchronized (ForecastDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            applicationContext,
                            ForecastDatabase.class,
                            DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract CurrentDao getCurrentDao();

    public abstract DailyDao getDailyDao();

    public abstract HourlyDao getHourlyDao();

    public abstract CurrentLocationDao getCurrentLocationDao();

    public abstract UpdateTimeDao getUpdateTimeDao();
}
