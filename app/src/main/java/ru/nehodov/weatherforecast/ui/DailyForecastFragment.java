package ru.nehodov.weatherforecast.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.nehodov.weatherforecast.adapters.DailyForecastAdapter;
import ru.nehodov.weatherforecast.R;
import ru.nehodov.weatherforecast.SelectedDayListener;
import ru.nehodov.weatherforecast.utils.DailyDiffUtilCallback;
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel;

public class DailyForecastFragment extends Fragment
        implements SelectedDayListener {

    private static final String TAG = "DailyForecastFragment";

    private ForecastViewModel viewModel;

    private DailyForecastAdapter adapter;

    private DailyDiffUtilCallback diffUtilCallback;
    private DiffUtil.DiffResult diffResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ForecastViewModel.class);
        adapter = new DailyForecastAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        RecyclerView recycler = view.findViewById(R.id.dailyRecycler);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        viewModel.getDailyForecast().observe(getViewLifecycleOwner(), dailyForecasts -> {
            diffUtilCallback = new DailyDiffUtilCallback(
                    adapter.getDailyForecasts(), dailyForecasts);
            diffResult = DiffUtil.calculateDiff(diffUtilCallback);
            adapter.setDailyForecasts(dailyForecasts);
            diffResult.dispatchUpdatesTo(adapter);
        });
        return view;
    }

    @Override
    public void selectDay(Integer selectedDay) {
        viewModel.setSelectedDay(selectedDay);
    }
}