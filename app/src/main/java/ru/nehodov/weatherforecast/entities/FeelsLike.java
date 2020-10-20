package ru.nehodov.weatherforecast.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class FeelsLike {

    private String day;

    private String night;

    public FeelsLike(String day, String night) {
        this.day = day;
        this.night = night;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeelsLike feelsLike = (FeelsLike) o;
        return Objects.equals(day, feelsLike.day) && Objects.equals(night, feelsLike.night);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, night);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FeelsLike.class.getSimpleName() + "[", "]")
                .add("day='" + day + "'")
                .add("night='" + night + "'")
                .toString();
    }
}
