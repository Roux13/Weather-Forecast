package ru.nehodov.weatherforecast.repositories

import android.location.Location
import android.util.Log
import ru.nehodov.weatherforecast.entities.CurrentLocation
import ru.nehodov.weatherforecast.entities.TimeUpdate

interface IForecastGateway {
    suspend fun updateForecast(location: Location)
}

class ForecastGateway(
    private val forecastWebRepository: IForecastWebRepository,
    private val currentDbRepository: ICurrentDbRepository,
    private val dailiesDbRepository: IDailiesDbRepository,
    private val hourliesDbRepository: IHourliesDbRepository,
    private val currentLocationDbRepository: ICurrentLocationDbRepository,
    private val updateTimeRepository: IUpdateTimeRepository,
) : IForecastGateway {

    companion object {
        private const val TAG = "Repository"
    }

    override suspend fun updateForecast(location: Location) {
        val forecast = forecastWebRepository.updateForecast(location)

        forecast?.let {
            currentDbRepository.deleteAll()
            currentDbRepository.insert(forecast.current)

            dailiesDbRepository.deleteAll()
            dailiesDbRepository.insert(forecast.daily)

            hourliesDbRepository.deleteAll()
            hourliesDbRepository.insert(forecast.hourly)

            val latitude: Double = forecast.latitude
            val longitude: Double = forecast.longitude
            Log.d(
                TAG, String.format(
                    "Repository updated current location,"
                            + " latitude: %f, longitude: %f", latitude, longitude
                )
            )
            currentLocationDbRepository.deleteAll()
            currentLocationDbRepository.insert(
                CurrentLocation(
                    timezone = forecast.timezone,
                    latitude = latitude,
                    longitude = longitude
                )
            )

            updateTimeRepository.deleteAll()
            updateTimeRepository.insert(TimeUpdate(time = forecast.current.dateTime))
        }
    }
}