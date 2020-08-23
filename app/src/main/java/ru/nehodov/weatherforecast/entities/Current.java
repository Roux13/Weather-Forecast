package ru.nehodov.weatherforecast.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import ru.nehodov.weatherforecast.WeatherArrayConverter;

@Entity
public class Current {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("room_id")
    private long id;

    private String sunrise;

    private String temp;

    private String visibility;

    private String uvi;

    private String pressure;

    private String clouds;

    @SerializedName("dt")
    private String dateTime;

    private String sunset;

    @TypeConverters(WeatherArrayConverter.class)
    private Weather[] weather;

    private String humidity;
    @SerializedName("wind_speed")
    private String windSpeed;

    @SuppressWarnings("checkstyle:ParameterNumber")
    public Current(long id, String sunrise, String temp,
                   String visibility, String uvi, String pressure,
                   String clouds, String dateTime, String sunset,
                   Weather[] weather, String humidity, String windSpeed) {
        this.id = id;
        this.sunrise = sunrise;
        this.temp = temp;
        this.visibility = visibility;
        this.uvi = uvi;
        this.pressure = pressure;
        this.clouds = clouds;
        this.dateTime = dateTime;
        this.sunset = sunset;
        this.weather = weather;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getUvi() {
        return uvi;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @NotNull
    @Override
    public String toString() {
        return "Current{"
                + "sunrise='" + sunrise + '\''
                + ", temp='" + temp + '\''
                + ", visibility='" + visibility + '\''
                + ", uvi='" + uvi + '\''
                + ", pressure='" + pressure + '\''
                + ", clouds='" + clouds + '\''
                + ", dt='" + dateTime + '\'' + ", windDeg='"
                + sunset + '\'' + ", weather="
                + Arrays.toString(weather) + ", humidity='"
                + humidity + '\'' + ", windSpeed='"
                + windSpeed + '\'' + '}';
    }
}
