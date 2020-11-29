package ru.nehodov.weatherforecast.viewmodels

import android.location.Location
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.nehodov.weatherforecast.entities.CurrentLocation
import ru.nehodov.weatherforecast.entities.Daily
import ru.nehodov.weatherforecast.entities.Hourly
import ru.nehodov.weatherforecast.repository.ForecastRepositoryKot

class ForecastViewModelKot @ViewModelInject constructor(
        @Assisted private val savedInstanceState: SavedStateHandle,
        private val repository: ForecastRepositoryKot
        )
    : ViewModel() {

    companion object {
        private const val TODAY = 0
    }

    private val currentWeather: LiveData<Daily?> = repository.currentWeatherData()
    val dailyForecast: LiveData<MutableList<Daily?>?> = repository.dailyForecastData()
    private val hourlyForecast: LiveData<MutableList<Hourly?>?> = repository.hourlyForecastData()
    val currentLocation: LiveData<CurrentLocation?> = repository.currentLocationData()
    val timeUpdate: LiveData<String?> = repository.updateTimeData()

    val selectedDay = MutableLiveData(TODAY)
    val locationTitle = MutableLiveData("")

    fun updateForecast(location: Location) {
        repository.updateForecast(location)
    }

    fun setSelectedDay(selectedDay: Int) {
        this.selectedDay.value = selectedDay
    }

    fun setLocationTitle(title: String) {
        this.locationTitle.value = title
    }

}