package ru.nehodov.weatherforecast.di.modules

import org.koin.dsl.module
import ru.nehodov.weatherforecast.repository.ForecastRepositoryKot

object DataModule {

    val dataModule = module {

        single { ForecastRepositoryKot(get(), get()) }

    }

}