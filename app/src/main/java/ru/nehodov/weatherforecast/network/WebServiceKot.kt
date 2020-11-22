package ru.nehodov.weatherforecast.network

import javax.inject.Inject

class WebServiceKot {

    @Inject
    lateinit var api: OpenWeatherApi

}