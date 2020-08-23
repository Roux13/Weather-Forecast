package ru.nehodov.weatherforecast.entities;

import org.jetbrains.annotations.NotNull;

public class Temp {

    private String min;

    private String max;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @NotNull
    @Override
    public String toString() {
        return "Temp{"
                + "min='" + min + '\''
                + ", max='" + max + '\''
                + '}';
    }
}
