package ru.nehodov.weatherforecast.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.nehodov.weatherforecast.ForecastUpdateWorker;
import ru.nehodov.weatherforecast.di.modules.AppModule;
import ru.nehodov.weatherforecast.di.modules.DatabaseModule;
import ru.nehodov.weatherforecast.di.modules.NetworkModule;
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel;

@Component (modules = {AppModule.class, NetworkModule.class, DatabaseModule.class})
@Singleton
public interface AppComponent {

    void inject(ForecastViewModel viewModel);

    void inject(ForecastUpdateWorker updateWorker);
}
