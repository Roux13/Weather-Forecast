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
import ru.nehodov.weatherforecast.repository.ForecastRepository

private const val TODAY = 0

class ForecastViewModelKot @ViewModelInject constructor(
        @Assisted private val savedInstanceState: SavedStateHandle,
        private val repository: ForecastRepository
        )
    : ViewModel() {

    private val currentWeather: LiveData<Daily> = repository.currentWeather
    val dailyForecast: LiveData<MutableList<Daily>> = repository.dailyForecast
    private val hourlyForecast: LiveData<MutableList<Hourly>> = repository.hourlyForecast
    val currentLocation: LiveData<CurrentLocation> = repository.currentLocation
    val updateTime: LiveData<String> = repository.updateTime

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