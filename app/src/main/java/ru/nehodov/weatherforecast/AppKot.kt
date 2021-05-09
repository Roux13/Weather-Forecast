package ru.nehodov.weatherforecast

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.nehodov.weatherforecast.di.modules.DataModule
import ru.nehodov.weatherforecast.di.modules.DatabaseModuleKot
import ru.nehodov.weatherforecast.di.modules.NetworkModuleKot
import ru.nehodov.weatherforecast.di.modules.VieModelModule

class AppKot : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppKot)

            modules(
                DatabaseModuleKot.databaseModule,
                DataModule.dataModule,
                NetworkModuleKot.networkModule,
                VieModelModule.module
            )
        }
    }
}