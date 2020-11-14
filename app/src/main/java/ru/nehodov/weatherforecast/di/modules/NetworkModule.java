package ru.nehodov.weatherforecast.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nehodov.weatherforecast.network.NetworkContract;
import ru.nehodov.weatherforecast.network.OpenWeatherApi;
import ru.nehodov.weatherforecast.network.WebService;

@InstallIn(ApplicationComponent.class)
@Module
public class NetworkModule {

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(NetworkContract.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @Singleton
    OpenWeatherApi provideOpenWeatherApi(Retrofit retrofit) {
        return retrofit.create(OpenWeatherApi.class);
    }

    @Provides
    @Singleton
    WebService provideWebService() {
        return new WebService();
    }

}
