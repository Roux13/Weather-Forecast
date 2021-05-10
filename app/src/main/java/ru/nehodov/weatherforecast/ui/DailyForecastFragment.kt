package ru.nehodov.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import ru.nehodov.weatherforecast.R
import ru.nehodov.weatherforecast.SelectedDayListener
import ru.nehodov.weatherforecast.adapters.DailyForecastAdapter
import ru.nehodov.weatherforecast.utils.DailyDiffUtilCallback
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel

@KoinApiExtension
class DailyForecastFragment : Fragment(), SelectedDayListener, KoinComponent {

    companion object {
        private const val TAG = "DailyForecastFragment"
    }

    private val viewModel: ForecastViewModel by sharedViewModel()

    private lateinit var adapter: DailyForecastAdapter

    private lateinit var diffUtilCallback: DailyDiffUtilCallback
    private lateinit var diffResult: DiffResult

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_daily_forecast, container, false)
        adapter = DailyForecastAdapter(this)
        val recycler: RecyclerView = view.findViewById(R.id.dailyRecycler)
        recycler.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@DailyForecastFragment.adapter
        }
        viewModel.dailyForecast.observe(viewLifecycleOwner) {
            if (it != null) {
                diffUtilCallback = DailyDiffUtilCallback(adapter.getDailyForecasts(), newList = it)
                diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                adapter.setDailyForecasts(it)
                diffResult.dispatchUpdatesTo(adapter)
            }
        }
        return view
    }

    override fun selectDay(selectedDay: Int) {
        viewModel.setSelectedDay(selectedDay)
    }
}