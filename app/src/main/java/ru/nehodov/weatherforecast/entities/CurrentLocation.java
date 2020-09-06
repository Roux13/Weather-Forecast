package ru.nehodov.weatherforecast.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.StringJoiner;

@Entity
public class CurrentLocation {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String timezone;

    private double latitude;

    private double longitude;

    public CurrentLocation(long id, String timezone, double latitude, double longitude) {
        this.id = id;
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Ignore
    public CurrentLocation(String timezone, double latitude, double longitude) {
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CurrentLocation.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("timezone='" + timezone + "'")
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .toString();
    }
}
