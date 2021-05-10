package ru.nehodov.weatherforecast

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.nehodov.weatherforecast.di.modules.DataModule
import ru.nehodov.weatherforecast.di.modules.DatabaseModule
import ru.nehodov.weatherforecast.di.modules.NetworkModule
import ru.nehodov.weatherforecast.di.modules.ViewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                DatabaseModule.module,
                DataModule.module,
                NetworkModule.module,
                ViewModelModule.module
            )
        }
    }
}