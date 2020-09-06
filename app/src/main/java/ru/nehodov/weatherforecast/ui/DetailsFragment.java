package ru.nehodov.weatherforecast.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import ru.nehodov.weatherforecast.R;
import ru.nehodov.weatherforecast.utils.WeatherUtil;
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel;

public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";

    private static final String WEATHER_ICONS_URL = "https://openweathermap.org/img/wn/%s@2x.png";

    private static final Integer TODAY = 0;

    private ForecastViewModel viewModel;

    private TextView updateTimeTv;
    private TextView dateTV;
    private ImageView detailsIcon;
    private TextView detailsTempTV;
    private TextView descriptionTV;
    private TextView feelsLikeTV;
    private TextView humidity;
    private TextView pressure;
    private TextView uvi;
    private TextView windSpeed;

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ForecastViewModel.class);
        updateTimeTv = view.findViewById(R.id.updatedTimeTV);
        dateTV = view.findViewById(R.id.detailsDateTV);
        detailsIcon = view.findViewById(R.id.detailsIcon);
        detailsTempTV = view.findViewById(R.id.detailsTempTV);
        descriptionTV = view.findViewById(R.id.weatherDescriptionTV);
        feelsLikeTV = view.findViewById(R.id.feelsLikeTV);
        humidity = view.findViewById(R.id.humidityTV);
        pressure = view.findViewById(R.id.pressureTv);
        uvi = view.findViewById(R.id.uviTv);
        windSpeed = view.findViewById(R.id.windSpeedTv);
        viewModel.getSelectedDayData().observe(getViewLifecycleOwner(), day -> {
            if (day.equals(TODAY)) {
                displayCurrentWeather();
            } else {
                displaySelectedDay(day);
            }
        });
        return view;
    }

    private void displayCurrentWeather() {
        viewModel.getDailyForecast().removeObservers(getViewLifecycleOwner());
        viewModel.getCurrentWeather().observe(getViewLifecycleOwner(), currentWeather -> {
            if (currentWeather != null) {
                dateTV.setText(WeatherUtil.formatDate(currentWeather.getDateTime()));
                updateTimeTv.setText(String.format(getString(R.string.time_update_pattern),
                        WeatherUtil.formatTime(currentWeather.getDateTime())));
                detailsTempTV.setText(WeatherUtil.formatTemp(currentWeather.getTemp()));
                humidity.setText(String.format(
                        getString(R.string.humidity_pattern), currentWeather.getHumidity()));
                pressure.setText(String.format(
                        getString(R.string.pressure_pattern), currentWeather.getPressure()));
                uvi.setText(String.format(
                        getString(R.string.uvi_pattern), currentWeather.getUvi()));
                windSpeed.setText(
                        String.format(getString(
                                R.string.wind_wpeed_pattern), currentWeather.getWindSpeed()));
                descriptionTV.setText(currentWeather.getWeather()[0].getDescription());
                feelsLikeTV.setEnabled(true);
                feelsLikeTV
                        .setText(String.format(getString(R.string.feels_like_pattern),
                                WeatherUtil.formatTemp(currentWeather.getFeelsLike())));
                Picasso.get()
                        .load(String.format(
                                WEATHER_ICONS_URL,
                                currentWeather.getWeather()[0].getIcon()))
                        .resize(300, 300)
                        .into(detailsIcon);
            }
        });
    }

    public void displaySelectedDay(final Integer selectedDay) {
        viewModel.getCurrentWeather().removeObservers(getViewLifecycleOwner());
        viewModel.getDailyForecast().observe(getViewLifecycleOwner(), dailies -> {
            if (!dailies.isEmpty()) {
                dateTV.setText(WeatherUtil.formatDate(dailies.get(selectedDay).getDateTime()));
                detailsTempTV.setText(
                        WeatherUtil.formatTemp(dailies.get(selectedDay).getTemp().getMax()));
                humidity.setText(String.format(
                        getString(R.string.humidity_pattern),
                        dailies.get(selectedDay).getHumidity()));
                pressure.setText(String.format(
                        getString(R.string.pressure_pattern),
                        dailies.get(selectedDay).getPressure()));
                uvi.setText(String.format(
                        getString(R.string.uvi_pattern), dailies.get(selectedDay).getUvi()));
                windSpeed.setText(String.format(
                        getString(R.string.wind_wpeed_pattern),
                        dailies.get(selectedDay).getWindSpeed()));
                descriptionTV.setText(dailies.get(selectedDay).getWeather()[0].getDescription());
                feelsLikeTV.setEnabled(false);
                Picasso.get()
                        .load(String.format(
                                WEATHER_ICONS_URL,
                                dailies.get(selectedDay).getWeather()[0].getIcon()))
                        .resize(300, 300)
                        .into(detailsIcon);

            }
        });
    }
}