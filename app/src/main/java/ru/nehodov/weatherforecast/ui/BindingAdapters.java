package ru.nehodov.weatherforecast.ui;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.network.NetworkContract;

import static ru.nehodov.weatherforecast.network.NetworkContract.WEATHER_ICONS_URL;

public class BindingAdapters {
    @BindingAdapter(value = {"loadWeatherIcon", "isMini"}, requireAll = false)
    public static void loadWeatherIcon(ImageView view, Daily daily, boolean isMini) {
        if (isMini) {
            loadMiniIcon(view, daily);
        } else {
            loadDefaultIcon(view, daily);
        }
    }

    private static void loadDefaultIcon(ImageView view, Daily daily) {
        if (null == daily || null == daily.getWeather()) {
            return;
        }
        Picasso.get()
                .load(String.format(
                        NetworkContract.WEATHER_ICONS_URL,
                        daily.getWeather()[0].getIcon()))
                .resize(300, 300)
                .into(view);
    }

    private static void loadMiniIcon(ImageView view, Daily daily) {
        if (null == daily || null == daily.getWeather()) {
            return;
        }
        Picasso.get()
                .load(String.format(WEATHER_ICONS_URL, daily.getWeather()[0].getIcon()))
                .resize(128, 128)
                .into(view);
    }
}
