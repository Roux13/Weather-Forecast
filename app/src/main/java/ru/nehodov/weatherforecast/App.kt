package ru.nehodov.weatherforecast

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.nehodov.weatherforecast.di.modules.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                DatabaseModule.module,
                DataModule.module,
                NetworkModule.module,
                ViewModelModule.module,
                LocationModule.locationModule
            )
        }
    }
}