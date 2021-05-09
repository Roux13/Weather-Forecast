package ru.nehodov.weatherforecast.di.modules

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApiKot
import ru.nehodov.weatherforecast.network.WebServiceKot
object NetworkModuleKot {

    val networkModule = module {

        factory { GsonConverterFactory.create() }

        fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
            Retrofit.Builder()
                .baseUrl(NetworkContract.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build()

        single { provideRetrofit(get()) }

        fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApiKot =
            retrofit.create(OpenWeatherApiKot::class.java)

        single { provideOpenWeatherApi(get()) }

        single { WebServiceKot() }

    }
}