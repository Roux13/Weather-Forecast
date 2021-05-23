package ru.nehodov.weatherforecast.viewmodels

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
        val TAG: String = ForecastViewModel::class.java.simpleName

        const val TODAY = 0
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

    private val currentRepository: ICurrentDbRepository by inject()
    private val dailyRepository: IDailiesDbRepository by inject()
    private val currentLocationRepository: ICurrentLocationDbRepository by inject()
    private val timeUpdateRepository: IUpdateTimeRepository by inject()
    private val forecastGateway: IForecastGateway by inject()
    private val addressRepository: IAddressRepository by inject()

    private val _selectedDayWeather: MutableStateFlow<Daily> =
        MutableStateFlow(Daily(feelsLike = FeelsLike(), temp = Temp()))
    val selectedDayWeather: StateFlow<Daily>
        get() = _selectedDayWeather

    private val _currentForecast: MutableStateFlow<Current> = MutableStateFlow(Current())
    val currentForecast: StateFlow<Current>
        get() = _currentForecast

    private val _dailyForecast: MutableStateFlow<List<Daily>> = MutableStateFlow(emptyList())
    val dailyForecast: StateFlow<List<Daily>>
        get() = _dailyForecast

    private val _hourlyForecast: MutableStateFlow<List<Hourly>> = MutableStateFlow(emptyList())
    val hourlyForecast: StateFlow<List<Hourly>>
        get() = _hourlyForecast

    private val _currentLocation: MutableStateFlow<CurrentLocation> = MutableStateFlow(
        CurrentLocation()
    )
    val currentLocation: StateFlow<CurrentLocation>
        get() = _currentLocation

    private val _updateTime: MutableStateFlow<String> = MutableStateFlow("")
    val updateTime: StateFlow<String>
        get() = _updateTime

    val selectedDay = MutableStateFlow<Int>(TODAY)
    val locationTitle = MutableLiveData("")

    init {
        subscribeCurrentLocationData()
        subscribeCurrentAddressData()
        subscribeCurrentWeatherData()
        subscribeDailiesData()
        subscribeTimeUpdateData()
        setSelectedDay(TODAY)
    }

    private fun subscribeCurrentLocationData() = viewModelScope.launch(coroutineExceptionHandler) {
        currentLocationRepository.currentLocationData().collect { currentLocation ->
            _currentLocation.value = currentLocation
        }
    }

    private fun subscribeCurrentAddressData() = viewModelScope.launch(coroutineExceptionHandler) {
        currentLocation.collect { currentLocation: CurrentLocation? ->
            if (currentLocation != null) {
                Log.d(
                    TAG, String.format(
                        "Current location is not null, latitude: %f, longitude: %f",
                        currentLocation.latitude, currentLocation.longitude
                    )
                )
                setLocationTitle(addressRepository.getCurrentAddress(currentLocation))
            }
        }
    }

    private fun subscribeCurrentWeatherData() = viewModelScope.launch(coroutineExceptionHandler) {
        currentRepository.getCurrentWeather().collect { current ->
            _currentForecast.value = current
        }
    }

    private fun subscribeDailiesData() = viewModelScope.launch(coroutineExceptionHandler) {
        dailyRepository.getDailies().collect { dailies ->
            _dailyForecast.value = dailies
        }
    }

    private fun subscribeTimeUpdateData() = viewModelScope.launch(coroutineExceptionHandler) {
        timeUpdateRepository.timeUpdateData().collect { updateTime ->
            _updateTime.value = updateTime
        }
    }

    fun updateForecast(location: Location) = viewModelScope.launch(coroutineExceptionHandler) {
        forecastGateway.updateForecast(location)
    }

    fun setSelectedDay(selectedDay: Int) {
        this.selectedDay.value = selectedDay
        viewModelScope.launch(coroutineExceptionHandler) {
            _dailyForecast.collect { dailies ->
                if (dailies.lastIndex >= selectedDay) {
                    if (selectedDay == 0) {
                        val currentWeatherAsDaily =
                            CurrentToDailyConverter.convert(_currentForecast.value)
                        _selectedDayWeather.value = currentWeatherAsDaily
                    } else {
                        _selectedDayWeather.value = dailies[selectedDay]
                    }
                }
            }
        }
    }

    private fun setLocationTitle(title: String) {
        this.locationTitle.value = title
    }

}