package ru.nehodov.weatherforecast.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

import ru.nehodov.weatherforecast.utils.WeatherArrayConverter;

@Entity
public class Daily {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("room_id")
    private long id;

    private String sunrise;

    @Embedded
    private Temp temp;

    @Embedded
    @SerializedName("feels_like")
    private FeelsLike feelsLike;

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
    public Daily(long id, String sunrise, Temp temp, FeelsLike feelsLike,
                 String uvi, String pressure, String clouds,
                 String dateTime, String sunset, Weather[] weather,
                 String humidity, String windSpeed) {
        this.id = id;
        this.sunrise = sunrise;
        this.temp = temp;
        this.feelsLike = feelsLike;
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

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(FeelsLike feelsLike) {
        this.feelsLike = feelsLike;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Daily daily = (Daily) o;
        return id == daily.id && Objects.equals(sunrise, daily.sunrise)
                && Objects.equals(temp, daily.temp)
                && Objects.equals(feelsLike, daily.feelsLike)
                && Objects.equals(uvi, daily.uvi)
                && Objects.equals(pressure, daily.pressure)
                && Objects.equals(clouds, daily.clouds)
                && Objects.equals(dateTime, daily.dateTime)
                && Objects.equals(sunset, daily.sunset)
                && Arrays.equals(weather, daily.weather)
                && Objects.equals(humidity, daily.humidity)
                && Objects.equals(windSpeed, daily.windSpeed);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(
                id, sunrise, temp, feelsLike,
                uvi, pressure, clouds, dateTime,
                sunset, humidity, windSpeed);
        result = 31 * result + Arrays.hashCode(weather);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Daily.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("sunrise='" + sunrise + "'")
                .add("temp=" + temp)
                .add("feelsLike=" + feelsLike)
                .add("uvi='" + uvi + "'")
                .add("pressure='" + pressure + "'")
                .add("clouds='" + clouds + "'")
                .add("dateTime='" + dateTime + "'")
                .add("sunset='" + sunset + "'")
                .add("weather=" + Arrays.toString(weather))
                .add("humidity='" + humidity + "'")
                .add("windSpeed='" + windSpeed + "'")
                .toString();
    }
}
