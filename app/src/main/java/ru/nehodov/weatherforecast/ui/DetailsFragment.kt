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
import ru.nehodov.weatherforecast.utils.WeatherUtil
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel

@KoinApiExtension
class DetailsFragment : Fragment(), KoinComponent {

    private val TAG = "DetailsFragment"

    private val viewModel: ForecastViewModel by sharedViewModel()

    private val updateTimeObservable = ObservableField("")
    val dateTime = ObservableField("")
    val description = ObservableField("")
    private val iconPath = ObservableField("")
    private val maxTemp = ObservableField("")
    val feelsLike = ObservableField("")
    val humidity = ObservableField("")
    val uvi = ObservableField("")
    val pressure = ObservableField("")
    val windSpeed = ObservableField("")

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
        binding.dateTime = dateTime
        binding.description = description
        binding.iconPath = iconPath
        binding.maxTemp = maxTemp
        binding.feelsLike = feelsLike
        binding.humidity = humidity
        binding.uvi = uvi
        binding.pressure = pressure
        binding.windSpeed = windSpeed
        binding.timeUpdate = updateTimeObservable
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.selectedDayWeather.collect { selectedDailies ->
                dateTime.set(WeatherUtil.formatDate(selectedDailies.dateTime))
                if (selectedDailies.weather.isNotEmpty()) {
                    description.set(selectedDailies.weather[0].description)
                    iconPath.set(selectedDailies.weather[0].icon)
                }
                maxTemp.set(WeatherUtil.formatTemp(selectedDailies.temp.max))
                feelsLike.set(
                    getString(
                        R.string.feels_like_pattern, WeatherUtil.formatTemp(
                            selectedDailies.feelsLike.day
                        )
                    )
                )
                humidity.set(
                    String.format(
                        getString(R.string.humidity_pattern),
                        selectedDailies.humidity
                    )
                )
                uvi.set((getString(R.string.uvi_pattern, selectedDailies.uvi)))
                pressure.set(getString(R.string.pressure_pattern, selectedDailies.pressure))
                windSpeed.set(getString(R.string.wind_wpeed_pattern, selectedDailies.windSpeed))
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