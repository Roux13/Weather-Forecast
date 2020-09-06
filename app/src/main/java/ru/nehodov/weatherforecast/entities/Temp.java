package ru.nehodov.weatherforecast.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Temp temp = (Temp) o;
        return Objects.equals(min, temp.min)
                && Objects.equals(max, temp.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
