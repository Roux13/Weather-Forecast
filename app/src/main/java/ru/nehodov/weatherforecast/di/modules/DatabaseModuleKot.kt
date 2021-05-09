package ru.nehodov.weatherforecast.di.modules

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.nehodov.weatherforecast.database.ForecastDatabaseKot

object DatabaseModuleKot {

    val databaseModule = module {

        single { ForecastDatabaseKot.getInstance(androidContext()) }

    }
}