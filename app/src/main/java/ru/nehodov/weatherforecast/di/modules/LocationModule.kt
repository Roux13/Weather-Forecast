package ru.nehodov.weatherforecast.di.modules

import android.location.Geocoder
import org.koin.dsl.module

object LocationModule {

    val locationModule = module {
        single { Geocoder(get()) }
    }
}