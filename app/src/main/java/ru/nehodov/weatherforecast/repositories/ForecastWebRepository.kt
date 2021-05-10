package ru.nehodov.weatherforecast.repositories

import android.location.Location
import retrofit2.Call
import ru.nehodov.weatherforecast.entities.Forecast
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApi

interface IForecastWebRepository {
    fun updateForecast(location: Location): Call<Forecast>

}

class ForecastWebRepository(
    private val weatherApi: OpenWeatherApi
) : IForecastWebRepository {
    override fun updateForecast(location: Location): Call<Forecast> {
        return weatherApi.getForecast(
            location.latitude.toString(),
            location.longitude.toString(),
            NetworkContract.WEATHER_API_KEY
        )
    }
}