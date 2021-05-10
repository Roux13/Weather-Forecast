package ru.nehodov.weatherforecast.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel

@KoinApiExtension
object ViewModelModule {
    val module = module {
        viewModel { ForecastViewModel() }
    }
}