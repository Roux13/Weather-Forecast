package ru.nehodov.weatherforecast.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModelKot

object VieModelModule {
    val module = module {
        viewModel { ForecastViewModelKot() }
    }
}