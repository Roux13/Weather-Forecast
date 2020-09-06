package ru.nehodov.weatherforecast.repository;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
import ru.nehodov.weatherforecast.network.OpenWeatherApi;
import ru.nehodov.weatherforecast.network.WebService;

public class ForecastRepository {

    private static final String TAG = "Repository";

    private static final String WEATHER_API_KEY = "d9a386e4aee859ca058160aa3647c436";

    private final OpenWeatherApi weatherApi;
    private final CurrentDao currentDao;
    private final DailyDao dailyDao;
    private final HourlyDao hourlyDao;
    private final CurrentLocationDao currentLocationDao;

    public ForecastRepository(Application application) {
        this.weatherApi = new WebService().getApi();
        ForecastDatabase db = ForecastDatabase.getInstance(application);
        this.currentDao = db.getCurrentDao();
        this.dailyDao = db.getDailyDao();
        this.hourlyDao = db.getHourlyDao();
        this.currentLocationDao = db.getCurrentLocationDao();
    }

    public ForecastRepository(Context applicationContext) {
        this.weatherApi = new WebService().getApi();
        ForecastDatabase db = ForecastDatabase.getInstance(applicationContext);
        this.currentDao = db.getCurrentDao();
        this.dailyDao = db.getDailyDao();
        this.hourlyDao = db.getHourlyDao();
        this.currentLocationDao = db.getCurrentLocationDao();
    }

    public void updateForecast(final Location location) {
        Log.d(TAG, String.format("in refreshForecast, Location is %s", location.toString()));
        Log.d(TAG, String.format("To request latitude: %s, longitude:%s",
                location.getLatitude(), location.getLongitude()));
        Call<Forecast> call = weatherApi.getForecast(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()), WEATHER_API_KEY);
        Log.d(TAG, call.toString());
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Request is successful body: " + response.body().toString());
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

    public CurrentLocation getCurrentLocationWithoutLiveData() {
        return currentLocationDao.getWithouLiveData();
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        ForecastDatabase.DB_EXECUTOR_SERVICE.execute(
                () -> currentLocationDao.insert(currentLocation));
    }
}



