package ru.nehodov.weatherforecast.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApiKot
import ru.nehodov.weatherforecast.network.WebServiceKot
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModuleKot {

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
            .baseUrl(NetworkContract.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApiKot = retrofit.create(OpenWeatherApiKot::class.java)

    @Singleton
    @Provides
    fun provideWebService(): WebServiceKot = WebServiceKot()
}