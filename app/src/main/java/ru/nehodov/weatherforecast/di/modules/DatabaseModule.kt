package ru.nehodov.weatherforecast.di.modules

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.nehodov.weatherforecast.database.ForecastDatabase

object DatabaseModule {

    val module = module {

        single { ForecastDatabase.getInstance(androidContext()) }

    }
}