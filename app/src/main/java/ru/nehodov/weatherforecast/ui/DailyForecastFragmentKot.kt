package ru.nehodov.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nehodov.weatherforecast.R
import ru.nehodov.weatherforecast.SelectedDayListenerKot
import ru.nehodov.weatherforecast.adapters.DailyForecastAdapterKot
import ru.nehodov.weatherforecast.utils.DailyDiffUtilCallbackKot
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModelKot

private const val TAG = "DailyForecastFragment"

@AndroidEntryPoint
class DailyForecastFragmentKot : Fragment(), SelectedDayListenerKot {

    private lateinit var viewModel: ForecastViewModelKot

    private lateinit var adapter: DailyForecastAdapterKot

    private lateinit var diffUtilCallback: DailyDiffUtilCallbackKot
    private lateinit var diffResult: DiffResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_daily_forecast, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ForecastViewModelKot::class.java)
        adapter = DailyForecastAdapterKot(this)
        val recycler: RecyclerView = view.findViewById(R.id.dailyRecycler)
        recycler.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@DailyForecastFragmentKot.adapter
        }
        viewModel.dailyForecast.observe(viewLifecycleOwner, {
            diffUtilCallback = DailyDiffUtilCallbackKot(adapter.getDailyForecasts(), newList = it)
            diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            adapter.setDailyForecasts(it)
            diffResult.dispatchUpdatesTo(adapter)
        })
        return view
    }

    override fun selectDay(selectedDay: Int) {
        viewModel.setSelectedDay(selectedDay)
    }
}