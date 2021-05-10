package ru.nehodov.weatherforecast.web

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.nehodov.weatherforecast.entities.Forecast
import ru.nehodov.weatherforecast.network.WebService
import java.io.IOException

class WebServiceTest {
    @Test
    fun apiConnectionTest() {
        val stringBuilder = StringBuilder()
        val webService = WebService()
        val api = webService.api
        try {
            val call: Forecast = runBlocking { api.getForecast(LATITUDE, LONGITUDE, API_KEY) }
            stringBuilder.append("Response is successful")
            stringBuilder.append(call)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        println(stringBuilder.toString())
    }

    companion object {
        private const val LATITUDE = "33.441792"
        private const val LONGITUDE = "-94.037689"
        private const val API_KEY = "d9a386e4aee859ca058160aa3647c436"
    }
}