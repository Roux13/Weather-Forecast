package ru.nehodov.weatherforecast.web;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {

    private static final String BASE_URL = "https://api.openweathermap.org/";

    private final OpenWeatherApi api;

    public WebService() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
//                .setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();

        api = retrofit.create(OpenWeatherApi.class);
    }

    public OpenWeatherApi getApi() {
        return api;
    }
}
