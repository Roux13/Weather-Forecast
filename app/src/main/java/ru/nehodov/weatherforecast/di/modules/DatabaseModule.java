package ru.nehodov.weatherforecast.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import ru.nehodov.weatherforecast.dao.CurrentDao;
import ru.nehodov.weatherforecast.dao.CurrentLocationDao;
import ru.nehodov.weatherforecast.dao.DailyDao;
import ru.nehodov.weatherforecast.dao.HourlyDao;
import ru.nehodov.weatherforecast.dao.UpdateTimeDao;
import ru.nehodov.weatherforecast.database.ForecastDatabase;

@InstallIn(ApplicationComponent.class)
@Module
public class DatabaseModule {

    @Singleton
    @Provides
    ForecastDatabase provideForecastDatabase(@ApplicationContext Context context) {
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
