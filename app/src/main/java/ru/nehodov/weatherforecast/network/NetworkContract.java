package ru.nehodov.weatherforecast.network;

import ru.nehodov.weatherforecast.BuildConfig;

public final class NetworkContract {

    public static final String BASE_URL = "https://api.openweathermap.org/";

    public static final String WEATHER_ICONS_URL = "https://openweathermap.org/img/wn/%s@2x.png";

    public static final String WEATHER_API_KEY = BuildConfig.API_KEY;
}
