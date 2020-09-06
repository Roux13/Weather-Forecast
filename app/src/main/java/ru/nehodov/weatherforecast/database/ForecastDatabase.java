package ru.nehodov.weatherforecast.database;

import android.app.Application;
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
import ru.nehodov.weatherforecast.entities.Current;
import ru.nehodov.weatherforecast.entities.CurrentLocation;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.Hourly;

@Database(entities = {Current.class, Daily.class, Hourly.class, CurrentLocation.class},
        version = 1, exportSchema = false)
public abstract class ForecastDatabase extends RoomDatabase {

    private static final String DB_NAME = "forecast_db";
    private static final int NUMBER_OF_THREADS = 4;

    private static ForecastDatabase instance;

    public static final ExecutorService DB_EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CurrentDao getCurrentDao();

    public abstract DailyDao getDailyDao();

    public abstract HourlyDao getHourlyDao();

    public abstract CurrentLocationDao getCurrentLocationDao();

    public static ForecastDatabase getInstance(final Application application) {
        if (instance == null) {
            synchronized (ForecastDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            application.getApplicationContext(),
                            ForecastDatabase.class,
                            DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

    public static ForecastDatabase getInstance(final Context applicationContext) {
        if (instance == null) {
            synchronized (ForecastDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            applicationContext,
                            ForecastDatabase.class,
                            DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}
