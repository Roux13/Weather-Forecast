package ru.nehodov.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import ru.nehodov.weatherforecast.R
import ru.nehodov.weatherforecast.databinding.FragmentDetailsBinding
import ru.nehodov.weatherforecast.entities.DetailsDisplayEntity
import ru.nehodov.weatherforecast.utils.WeatherUtil
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel

@KoinApiExtension
class DetailsFragment : Fragment(), KoinComponent {

    private val TAG = "DetailsFragment"

    private val viewModel: ForecastViewModel by sharedViewModel()

    private val updateTimeObservable = ObservableField("")

    private val detailsObservable = ObservableField(DetailsDisplayEntity())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.detailsObservable = detailsObservable
        binding.timeUpdate = updateTimeObservable
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.selectedDayWeather.collect { selectedDailies ->
                val dateTime = WeatherUtil.formatDate(selectedDailies.dateTime)
                val description =
                    if (selectedDailies.weather.isNotEmpty()) selectedDailies.weather[0].description else ""
                val iconPath =
                    if (selectedDailies.weather.isNotEmpty()) selectedDailies.weather[0].icon else ""
                val maxTemp = WeatherUtil.formatTemp(selectedDailies.temp.max)
                val feelsLike =
                    getString(
                        R.string.feels_like_pattern, WeatherUtil.formatTemp(
                            selectedDailies.feelsLike.day
                        )
                    )
                val humidity =
                    String.format(
                        getString(R.string.humidity_pattern),
                        selectedDailies.humidity
                    )
                val uvi = (getString(R.string.uvi_pattern, selectedDailies.uvi))
                val pressure = getString(R.string.pressure_pattern, selectedDailies.pressure)
                val windSpeed = getString(R.string.wind_wpeed_pattern, selectedDailies.windSpeed)
                detailsObservable.set(
                    DetailsDisplayEntity(
                        dateTime,
                        description,
                        iconPath,
                        maxTemp,
                        feelsLike,
                        humidity,
                        uvi,
                        pressure,
                        windSpeed
                    )
                )
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.updateTime.collect { updateTime ->
                this@DetailsFragment.updateTimeObservable.set(
                    String.format(
                        getString(
                            R.string.time_update_pattern,
                            WeatherUtil.formatTime(updateTime)
                        )
                    )
                )
            }
        }
    }
}