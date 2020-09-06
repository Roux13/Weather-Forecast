package ru.nehodov.weatherforecast.web;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import ru.nehodov.weatherforecast.entities.Forecast;
import ru.nehodov.weatherforecast.network.OpenWeatherApi;
import ru.nehodov.weatherforecast.network.WebService;

public class WebServiceTest {

    private final static String LATITUDE = "33.441792";
    private final static String LONGITUDE = "-94.037689";
    private final static String API_KEY = "d9a386e4aee859ca058160aa3647c436";

    @Test
    public void apiConnectionTest() {
        StringBuilder stringBuilder = new StringBuilder();
        WebService webService = new WebService();
        OpenWeatherApi api = webService.getApi();
        Call<Forecast> call = api.getForecast(LATITUDE, LONGITUDE, API_KEY);
        try {
            Response<Forecast> response = call.execute();
            if (response.isSuccessful()) {
                    stringBuilder.append("Response is successful");
                    stringBuilder.append(response.body());
                } else {
                    stringBuilder.append(response.code()).append("%n").append(response.errorBody());
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
         System.out.println(stringBuilder.toString());
    }

}