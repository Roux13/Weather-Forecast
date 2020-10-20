package ru.nehodov.weatherforecast.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.nehodov.weatherforecast.dao.CurrentDao;
import ru.nehodov.weatherforecast.dao.CurrentLocationDao;
import ru.nehodov.weatherforecast.dao.DailyDao;
import ru.nehodov.weatherforecast.dao.HourlyDao;
import ru.nehodov.weatherforecast.dao.UpdateTimeDao;
import ru.nehodov.weatherforecast.database.ForecastDatabase;

@Module
public class DatabaseModule {

//    @Provides
//    @Singleton
//    provideContext() {
//
//    }

    @Singleton
    @Provides
    ForecastDatabase provideForecastDatabase(Context context) {
        return ForecastDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    CurrentDao provideCurrentDao(ForecastDatabase forecastDatabase) {
        return forecastDatabase.getCurrentDao();
    }

    @Singleton
    @Provides
    DailyDao provideDailyDao(ForecastDatabase forecastDatabase) {
        return forecastDatabase.getDailyDao();
    }

    @Singleton
    @Provides
    HourlyDao provideHourlyDao(ForecastDatabase forecastDatabase) {
        return forecastDatabase.getHourlyDao();
    }

    @Singleton
    @Provides
    CurrentLocationDao provideCurrentLocationDao(ForecastDatabase forecastDatabase) {
        return forecastDatabase.getCurrentLocationDao();
    }

    @Singleton
    @Provides
    UpdateTimeDao provideUpdateTiemDao(ForecastDatabase forecastDatabase) {
        return forecastDatabase.getUpdateTimeDao();
    }
}
