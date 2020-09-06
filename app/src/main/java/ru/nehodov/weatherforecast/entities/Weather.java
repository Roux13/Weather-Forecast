package ru.nehodov.weatherforecast.entities;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Weather {

    private String icon;

    private String description;

    private String main;

    @SerializedName("id")
    private String apiId;

    public Weather(String icon, String description, String main, String apiId) {
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.apiId = apiId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @NotNull
    @Override
    public String toString() {
        return "Weather{"
                + "icon='" + icon + '\''
                + ", description='" + description + '\''
                + ", main='" + main + '\''
                + ", id='" + apiId + '\''
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
        Weather weather = (Weather) o;
        return Objects.equals(icon, weather.icon)
                && Objects.equals(description, weather.description)
                && Objects.equals(main, weather.main)
                && Objects.equals(apiId, weather.apiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, description, main, apiId);
    }
}
