package ru.nehodov.weatherforecast;

import android.app.Application;

import ru.nehodov.weatherforecast.di.components.AppComponent;
//import ru.nehodov.weatherforecast.di.components.DaggerAppComponent;
import ru.nehodov.weatherforecast.di.components.DaggerAppComponent;
import ru.nehodov.weatherforecast.di.modules.AppModule;

public class App extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
