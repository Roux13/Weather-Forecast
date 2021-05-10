package ru.nehodov.weatherforecast.di.modules

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApi
import ru.nehodov.weatherforecast.network.WebService

object NetworkModule {

    val module = module {

        factory { GsonConverterFactory.create() }

        fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit =
            Retrofit.Builder()
                .baseUrl(NetworkContract.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build()

        single { provideRetrofit(get()) }

        fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi =
            retrofit.create(OpenWeatherApi::class.java)

        single { provideOpenWeatherApi(get()) }

        single { WebService() }

    }
}