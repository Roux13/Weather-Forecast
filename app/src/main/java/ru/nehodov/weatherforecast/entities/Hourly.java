package ru.nehodov.weatherforecast.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import ru.nehodov.weatherforecast.utils.WeatherArrayConverter;

@Entity
public class Hourly {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("room_id")
    private long id;

    @SerializedName("dt")
    private String dateTime;

    private String pop;

    private String temp;
    @TypeConverters(WeatherArrayConverter.class)
    private Weather[] weather;

    private String clouds;

    public Hourly(long id, String dateTime, String pop, String temp,
                  Weather[] weather, String clouds) {
        this.id = id;
        this.dateTime = dateTime;
        this.pop = pop;
        this.temp = temp;
        this.weather = weather;
        this.clouds = clouds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    @NotNull
    @Override
    public String toString() {
        return "Hourly{"
                + "dt='" + dateTime + '\''
                + ", pop='" + pop + '\''
                + ", temp='" + temp + '\''
                + ", weather=" + Arrays.toString(weather)
                + ", clouds='" + clouds + '\''
                + '}';
    }
}
