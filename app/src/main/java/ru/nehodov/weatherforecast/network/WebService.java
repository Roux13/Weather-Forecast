package ru.nehodov.weatherforecast.network;

import javax.inject.Inject;

public class WebService {

    @Inject
    OpenWeatherApi api;

    public WebService() {
    }

    public OpenWeatherApi getApi() {
        return api;
    }
}
