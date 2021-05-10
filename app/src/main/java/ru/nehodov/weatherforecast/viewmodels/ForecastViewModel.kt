package ru.nehodov.weatherforecast.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.nehodov.weatherforecast.entities.*
import ru.nehodov.weatherforecast.repositories.*
import ru.nehodov.weatherforecast.utils.CurrentToDailyConverter

@KoinApiExtension
class ForecastViewModel : ViewModel(), KoinComponent {

    companion object {
        private const val TODAY = 0
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    private val currentRepository: ICurrentDbRepository by inject()
    private val dailyRepository: IDailiesDbRepository by inject()
    private val currentLocationRepository: ICurrentLocationDbRepository by inject()
    private val timeUpdateRepository: IUpdateTimeRepository by inject()
    private val forecastGateway: IForecastGateway by inject()

    private val _selectedDayWeather: MutableLiveData<Daily> =
        MutableLiveData(Daily(feelsLike = FeelsLike(), temp = Temp()))
    val selectedDayWeather: LiveData<Daily> = _selectedDayWeather

    private val _dailyForecast: MutableLiveData<List<Daily>> = MutableLiveData(emptyList())
    val dailyForecast: LiveData<List<Daily>> = _dailyForecast

    private val _hourlyForecast: MutableLiveData<List<Hourly>> = MutableLiveData(emptyList())
    private val hourlyForecast: LiveData<List<Hourly>> = _hourlyForecast

    private val _currentLocation: MutableLiveData<CurrentLocation> = MutableLiveData(
        CurrentLocation()
    )
    val currentLocation: LiveData<CurrentLocation> = _currentLocation

    private val _timeUpdate: MutableLiveData<String> = MutableLiveData("")
    val timeUpdate: LiveData<String> = _timeUpdate

    private val selectedDay = MutableLiveData(TODAY)
    val locationTitle = MutableLiveData("")


    fun updateForecast(location: Location) = viewModelScope.launch(coroutineExceptionHandler) {
        forecastGateway.updateForecast(location)
        val currentWeatherAsDaily =
            CurrentToDailyConverter.convert(currentRepository.getCurrentWeather())
        val dailyList = listOf(currentWeatherAsDaily) + dailyRepository.getDailies()
        _dailyForecast.postValue(dailyList)
        _currentLocation.postValue(currentLocationRepository.currentLocationData())
        _timeUpdate.postValue(timeUpdateRepository.timeUpdateData())
        setSelectedDay(TODAY)
    }

    fun setSelectedDay(selectedDay: Int) {
        this.selectedDay.postValue(selectedDay)
        _selectedDayWeather.postValue(
            _dailyForecast.value?.get(selectedDay) ?: Daily(feelsLike = FeelsLike(), temp = Temp())
        )
    }

    fun setLocationTitle(title: String) {
        this.locationTitle.value = title
    }

}