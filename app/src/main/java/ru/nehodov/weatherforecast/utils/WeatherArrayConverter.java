package ru.nehodov.weatherforecast.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ru.nehodov.weatherforecast.entities.Weather;

public class WeatherArrayConverter {
    @TypeConverter
    public static String fromArray(Weather[] weathers) {
        Gson gson = new Gson();
        return gson.toJson(weathers);
    }

    @TypeConverter
    public static Weather[] fromString(String valu) {
        Type type = new TypeToken<Weather[]>() { }.getType();
        return new Gson().fromJson(valu, type);
    }
}
