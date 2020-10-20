package ru.nehodov.weatherforecast.utils;

import ru.nehodov.weatherforecast.entities.Current;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.entities.FeelsLike;
import ru.nehodov.weatherforecast.entities.Temp;

public class CurrentToDailyConverter {

    public static Daily convert(Current current) {
        Temp temp = new Temp(current.getTemp(), current.getTemp());
        FeelsLike feelsLike = new FeelsLike(current.getFeelsLike(), current.getFeelsLike());
        return new Daily(
                current.getId(),
                current.getSunrise(),
                temp,
                feelsLike,
                current.getUvi(),
                current.getPressure(),
                current.getClouds(),
                current.getDateTime(),
                current.getSunset(),
                current.getWeather(),
                current.getHumidity(),
                current.getWindSpeed()
        );
    }

}
