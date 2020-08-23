package ru.nehodov.weatherforecast.repository;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nehodov.weatherforecast.dao.CurrentDao;
import ru.nehodov.weatherforecast.dao.DailyDao;
import ru.nehodov.weatherforecast.dao.HourlyDao;
import ru.nehodov.weatherforecast.database.ForecastDatabase;
import ru.nehodov.weatherforecast.entities.Current;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.Forecast;
import ru.nehodov.weatherforecast.entities.Hourly;
import ru.nehodov.weatherforecast.web.OpenWeatherApi;
import ru.nehodov.weatherforecast.web.WebService;

public class ForecastRepository {

    private static final String TAG = ForecastRepository.class.getSimpleName();

    private static final String WEATHER_API_KEY = "d9a386e4aee859ca058160aa3647c436";

    private final OpenWeatherApi weatherApi;
    private final CurrentDao currentDao;
    private final DailyDao dailyDao;
    private final HourlyDao hourlyDao;

    public ForecastRepository(final CurrentDao currentDao,
                              final DailyDao dailyDao,
                              final HourlyDao hourlyDao) {
        this.weatherApi = new WebService().getApi();
        this.currentDao = currentDao;
        this.dailyDao = dailyDao;
        this.hourlyDao = hourlyDao;
    }

    public void refreshForecast(final Location location) {
        Call<Forecast> call = weatherApi.getForecast(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()), WEATHER_API_KEY);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    ForecastDatabase.DB_EXECUTOR_SERVICE.execute(() -> {
                        currentDao.deleteCurrentWeather();
                        dailyDao.deleteDailyForecast();
                        hourlyDao.deleteHourlyForecast();
                        currentDao.insertCurrentWeather(response.body().getCurrent());
                        dailyDao.insertDailyForecast(response.body().getDaily());
                        hourlyDao.insertHourlyForecast(response.body().getHourly());
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
}



