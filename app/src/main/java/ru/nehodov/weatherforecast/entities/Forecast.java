package ru.nehodov.weatherforecast.entities;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Forecast {

    private Current current;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lon")
    private String longitude;

    private Daily[] daily;

    private Hourly[] hourly;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Hourly[] getHourly() {
        return hourly;
    }

    public void setHourly(Hourly[] hourly) {
        this.hourly = hourly;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @NotNull
    @Override
    public String toString() {
        return "ApiResponse{"
                + "current=" + current
                + ", daily=" + Arrays.toString(daily)
                + ", lon='" + longitude + '\'' + ", hourly="
                + Arrays.toString(hourly) + ", lat='" + latitude + '\'' + '}';
    }
}

