package ru.nehodov.weatherforecast.repository;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nehodov.weatherforecast.dao.CurrentDao;
import ru.nehodov.weatherforecast.dao.CurrentLocationDao;
import ru.nehodov.weatherforecast.dao.DailyDao;
import ru.nehodov.weatherforecast.dao.HourlyDao;
import ru.nehodov.weatherforecast.database.ForecastDatabase;
import ru.nehodov.weatherforecast.entities.Current;
import ru.nehodov.weatherforecast.entities.CurrentLocation;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.Forecast;
import ru.nehodov.weatherforecast.entities.Hourly;
import ru.nehodov.weatherforecast.network.NetworkContract;
import ru.nehodov.weatherforecast.network.OpenWeatherApi;

public class ForecastRepository {

    private static final String TAG = "Repository";

    private OpenWeatherApi weatherApi;
    private CurrentDao currentDao;
    private DailyDao dailyDao;
    private HourlyDao hourlyDao;
    private CurrentLocationDao currentLocationDao;

    @Inject
    public ForecastRepository(OpenWeatherApi weatherApi, CurrentDao currentDao,
                              DailyDao dailyDao, HourlyDao hourlyDao,
                              CurrentLocationDao currentLocationDao) {
        this.weatherApi = weatherApi;
        this.currentDao = currentDao;
        this.dailyDao = dailyDao;
        this.hourlyDao = hourlyDao;
        this.currentLocationDao = currentLocationDao;
    }

    public void updateForecast(final Location location) {
        Call<Forecast> call = weatherApi.getForecast(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()), NetworkContract.WEATHER_API_KEY);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(@NotNull Call<Forecast> call,
                                   @NotNull Response<Forecast> response) {
                if (response.isSuccessful()) {
                    ForecastDatabase.DB_EXECUTOR_SERVICE.execute(() -> {
                        currentDao.deleteCurrentWeather();
                        dailyDao.deleteDailyForecast();
                        hourlyDao.deleteHourlyForecast();
                        currentLocationDao.deleteAll();
                        currentDao.insertCurrentWeather(response.body().getCurrent());
                        dailyDao.insertDailyForecast(response.body().getDaily());
                        hourlyDao.insertHourlyForecast(response.body().getHourly());
                        double latitude = response.body().getLatitude();
                        double longitude = response.body().getLongitude();
                        currentLocationDao.insert(new CurrentLocation(
                                response.body().getTimezone(),
                                latitude,
                                longitude));
                    });
                } else {
                    Log.d(TAG, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<Forecast> call, @NotNull Throwable t) {
                if (t.getMessage() != null) {
                    Log.d(TAG, t.getMessage());
                }
            }
        });
    }

    public LiveData<Current> getCurrentWeather() {
        return currentDao.getCurrentWeather();
    }

    public LiveData<List<Daily>> getDailyForecast() {
        return dailyDao.getDailyForecast();
    }

    public LiveData<List<Hourly>> getHourlyForecast() {
        return hourlyDao.getHourlyForecast();
    }

    public LiveData<CurrentLocation> getCurrentLocation() {
        return currentLocationDao.get();
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        ForecastDatabase.DB_EXECUTOR_SERVICE.execute(
                () -> currentLocationDao.insert(currentLocation));
    }
}



