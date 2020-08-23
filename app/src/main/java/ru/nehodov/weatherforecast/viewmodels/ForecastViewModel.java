package ru.nehodov.weatherforecast.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.nehodov.weatherforecast.database.ForecastDatabase;
import ru.nehodov.weatherforecast.entities.Current;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.Hourly;
import ru.nehodov.weatherforecast.repository.ForecastRepository;

public class ForecastViewModel extends AndroidViewModel {

    private final ForecastRepository repository;

    private final LiveData<Current> currentWeather;
    private final LiveData<List<Daily>> dailyForecast;
    private final LiveData<List<Hourly>> hourlyForecast;

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        ForecastDatabase db = ForecastDatabase.getInstance(application);
        repository = new ForecastRepository(
                db.getCurrentDao(),
                db.getDailyDao(),
                db.getHourlyDao());
        currentWeather = repository.getCurrentWeather();
        dailyForecast = repository.getDailyForecast();
        hourlyForecast = repository.getHourlyForecast();
    }

    public LiveData<Current> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<List<Daily>> getDailyForecast() {
        return dailyForecast;
    }

    public LiveData<List<Hourly>> getHourlyForecast() {
        return hourlyForecast;
    }
}
