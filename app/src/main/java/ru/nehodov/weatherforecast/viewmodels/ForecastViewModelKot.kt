package ru.nehodov.weatherforecast.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nehodov.weatherforecast.App
import ru.nehodov.weatherforecast.entities.CurrentLocation
import ru.nehodov.weatherforecast.entities.Daily
import ru.nehodov.weatherforecast.entities.Hourly
import ru.nehodov.weatherforecast.repository.ForecastRepository
import javax.inject.Inject

private const val TODAY = 0

class ForecastViewModelKot(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: ForecastRepository

    private val currentWeather: LiveData<Daily>
    val dailyForecast: LiveData<MutableList<Daily>>
    private val hourlyForecast: LiveData<MutableList<Hourly>>
    val currentLocation: LiveData<CurrentLocation>
    val updateTime: LiveData<String>

    val selectedDay = MutableLiveData(TODAY)
    val locationTitle = MutableLiveData("")

    init {
        (application.applicationContext as App).appComponent.inject(this)
        currentWeather = repository.currentWeather
        dailyForecast = repository.dailyForecast
        hourlyForecast = repository.hourlyForecast
        currentLocation = repository.currentLocation
        updateTime = repository.updateTime
    }

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