package ru.nehodov.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.nehodov.weatherforecast.R;
import ru.nehodov.weatherforecast.SelectedDayListener;
import ru.nehodov.weatherforecast.entities.Daily;
import ru.nehodov.weatherforecast.utils.WeatherUtil;

public class DailyForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String WEATHER_ICONS_URL = "https://openweathermap.org/img/wn/%s@2x.png";

    private final SelectedDayListener selectedDayListener;

    private final List<Daily> dailyForecasts = new ArrayList<>();

    public DailyForecastAdapter(SelectedDayListener selectedDayListener) {
        this.selectedDayListener = selectedDayListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_item, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Daily daily = dailyForecasts.get(position);
        holder.itemView.setOnClickListener(v -> selectedDayListener.selectDay(position));
        TextView dailyDateTV = holder.itemView.findViewById(R.id.textDailyDate);
        TextView maxDailyTempTV = holder.itemView.findViewById(R.id.txtMaxDailyTemp);
        TextView minDailyTempTV = holder.itemView.findViewById(R.id.txtMinDailyTemp);
        ImageView dailyWeatherIcon = holder.itemView.findViewById(R.id.imgDailyWeather);
        dailyDateTV.setText(WeatherUtil.formatDate(daily.getDateTime()));
        maxDailyTempTV.setText(WeatherUtil.formatTemp(daily.getTemp().getMax()));
        minDailyTempTV.setText(WeatherUtil.formatTemp(daily.getTemp().getMin()));
        Picasso.get()
                .load(String.format(WEATHER_ICONS_URL, daily.getWeather()[0].getIcon()))
                .resize(128, 128)
                .into(dailyWeatherIcon);
    }

    @Override
    public int getItemCount() {
        return dailyForecasts.size();
    }

    public void setDailyForecasts(List<Daily> dailyForecasts) {
        this.dailyForecasts.clear();
        this.dailyForecasts.addAll(dailyForecasts);
    }

    public List<Daily> getDailyForecasts() {
        return dailyForecasts;
    }
}
