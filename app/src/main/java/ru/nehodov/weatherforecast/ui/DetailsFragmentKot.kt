package ru.nehodov.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import ru.nehodov.weatherforecast.R
import ru.nehodov.weatherforecast.databinding.FragmentDetailsBinding
import ru.nehodov.weatherforecast.utils.WeatherUtilKot
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModelKot

@KoinApiExtension
class DetailsFragmentKot : Fragment(), KoinComponent {

    private val TAG = "DetailsFragment"

    val viewModel: ForecastViewModelKot by sharedViewModel()

    val dateTime = ObservableField<String>("")
    val description = ObservableField<String>("")
    val iconPath = ObservableField<String>("")
    val maxTemp = ObservableField<String>("")
    val feelsLike = ObservableField<String>("")
    val humidity = ObservableField<String>("")
    val uvi = ObservableField<String>("")
    val pressure = ObservableField<String>("")
    val windSpeed = ObservableField<String>("")
    val timeUpdate = ObservableField<String>("")

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
        binding.timeUpdate = timeUpdate
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dailyForecast.observe(viewLifecycleOwner) { dailies ->
            if (!dailies.isNullOrEmpty()) {
                val selectedDailies = dailies?.get(viewModel.selectedDay.value ?: 0)
                selectedDailies?.let { selectedDailies ->
                    dateTime.set(WeatherUtilKot.formatDate(selectedDailies.dateTime ?: ""))
                    description.set(selectedDailies.weather?.get(0)?.description ?: "")
                    iconPath.set(selectedDailies.weather?.get(0)?.icon ?: "")
                    maxTemp.set(WeatherUtilKot.formatTemp(selectedDailies.temp?.max ?: ""))
                    feelsLike.set(
                        getString(
                            R.string.feels_like_pattern, WeatherUtilKot.formatTemp(
                                selectedDailies.feelsLike?.day ?: ""
                            )
                        )
                    )
                    humidity.set(
                        String.format(
                            getString(R.string.humidity_pattern),
                            selectedDailies.humidity ?: ""
                        )
                    )
                    uvi.set(selectedDailies.uvi) ?: ""
                    pressure.set(selectedDailies.pressure) ?: ""
                    windSpeed.set(selectedDailies.windSpeed) ?: ""
                }
            }
        }
        viewModel.timeUpdate.observe(viewLifecycleOwner) { timeUpdate ->
            this.timeUpdate.set(
                String.format(
                    getString(
                        R.string.time_update_pattern,
                        WeatherUtilKot.formatTime(timeUpdate)
                    )
                )
            )
        }
    }
}