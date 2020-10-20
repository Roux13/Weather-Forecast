package ru.nehodov.weatherforecast.viewmodels;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import ru.nehodov.weatherforecast.App;
import ru.nehodov.weatherforecast.entities.CurrentLocation;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.Hourly;
import ru.nehodov.weatherforecast.repository.ForecastRepository;

public class ForecastViewModel extends AndroidViewModel {

    private final static Integer TODAY = 0;

    @Inject
    ForecastRepository repository;

    private final LiveData<Daily> currentWeather;
    private final LiveData<List<Daily>> dailyForecast;
    private final LiveData<List<Hourly>> hourlyForecast;
    private final LiveData<CurrentLocation> currentLocation;
    private final LiveData<String> updateTime;

    private final MutableLiveData<Integer> selectedDay = new MutableLiveData<>();
    private final MutableLiveData<String> locationTitle = new MutableLiveData<>();

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        ((App) application.getApplicationContext()).getAppComponent().inject(this);
        currentWeather = repository.getCurrentWeather();
        dailyForecast = repository.getDailyForecast();
        hourlyForecast = repository.getHourlyForecast();
        currentLocation = repository.getCurrentLocation();
        updateTime = repository.getUpdateTime();
        selectedDay.setValue(TODAY);
    }

    public LiveData<Daily> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<List<Daily>> getDailyForecast() {
        return dailyForecast;
    }

    public LiveData<List<Hourly>> getHourlyForecast() {
        return hourlyForecast;
    }

    public LiveData<CurrentLocation> getCurrentLocation() {
        return currentLocation;
    }

    public void updateForecast(Location location) {
        repository.updateForecast(location);
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        repository.setCurrentLocation(currentLocation);
    }

    public LiveData<Integer> getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(Integer selectedDay) {
        this.selectedDay.setValue(selectedDay);
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle.setValue(locationTitle);
    }

    public LiveData<String> getLocationTitle() {
        return locationTitle;
    }

    public LiveData<String> getUpdateTime() {
        return updateTime;
    }
}
