package ru.nehodov.weatherforecast.repositories

import android.location.Location
import ru.nehodov.weatherforecast.entities.Forecast
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApi

interface IForecastWebRepository {
    suspend fun updateForecast(location: Location): Forecast

}

class ForecastWebRepository(
    private val weatherApi: OpenWeatherApi
) : IForecastWebRepository {
    override suspend fun updateForecast(location: Location): Forecast {
        return weatherApi.getForecast(
            location.latitude.toString(),
            location.longitude.toString(),
            NetworkContract.WEATHER_API_KEY
        )
    }
}