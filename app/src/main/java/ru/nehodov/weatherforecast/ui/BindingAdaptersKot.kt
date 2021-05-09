package ru.nehodov.weatherforecast.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.nehodov.weatherforecast.network.NetworkContract.WEATHER_ICONS_URL

@BindingAdapter(value = ["app:loadWeatherIcon", "app:isMini"], requireAll = false)
fun loadWeatherIcon(view: ImageView, iconPath: String, isMini: Boolean) {
    iconPath?.let {
        if (isMini) {
            loadMiniIcon(view, iconPath)
        } else {
            loadDefaultIcon(view, iconPath)
        }
    }
}

private fun loadDefaultIcon(view: ImageView, iconPath: String) {
    Picasso.get()
        .load(String.format(WEATHER_ICONS_URL, iconPath))
        .resize(300, 300)
        .into(view)
}

private fun loadMiniIcon(view: ImageView, iconPath: String) {
    Picasso.get()
        .load(String.format(WEATHER_ICONS_URL, iconPath))
        .resize(128, 128)
        .into(view)
}
