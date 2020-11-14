package ru.nehodov.weatherforecast.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import ru.nehodov.weatherforecast.R;
import ru.nehodov.weatherforecast.databinding.FragmentDetailsBinding;
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModelKot;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater,
                        R.layout.fragment_details,
                        container,
                        false);
        ForecastViewModelKot viewModel =
                new ViewModelProvider(requireActivity()).get(ForecastViewModelKot.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }
}