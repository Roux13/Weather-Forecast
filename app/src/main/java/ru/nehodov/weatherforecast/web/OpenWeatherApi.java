package ru.nehodov.weatherforecast.web;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nehodov.weatherforecast.entities.Forecast;

public interface OpenWeatherApi {

    @GET("data/2.5/onecall?exclude=minutely&units=metric&lang=ru")
    Call<Forecast> getForecast(@Query("lat") String latitude,
                               @Query("lon") String longitude,
                               @Query("appid") String apiKey);

}
