package ru.nehodov.weatherforecast.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.nehodov.weatherforecast.entities.Forecast

interface OpenWeatherApi {

    //    @GET("data/2.5/onecall?exclude=minutely&units=metric&lang=ru")
    @GET("data/2.5/onecall?exclude=minutely&units=metric")
    suspend fun getForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ): Forecast

}