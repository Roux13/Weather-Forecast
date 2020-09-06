package ru.nehodov.weatherforecast.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.StringJoiner;

public class Forecast {

    private Current current;

    private String timezone;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("lon")
    private double longitude;

    private Daily[] daily;

    private Hourly[] hourly;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Hourly[] getHourly() {
        return hourly;
    }

    public void setHourly(Hourly[] hourly) {
        this.hourly = hourly;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Forecast.class.getSimpleName() + "[", "]")
                .add("timezone='" + timezone + "'")
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .add("current=" + current)
                .add("daily=" + Arrays.toString(daily))
                .add("hourly=" + Arrays.toString(hourly))
                .toString();
    }
}

