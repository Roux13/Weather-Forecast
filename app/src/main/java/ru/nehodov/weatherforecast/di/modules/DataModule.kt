package ru.nehodov.weatherforecast.di.modules

import org.koin.dsl.module
import ru.nehodov.weatherforecast.repositories.*

object DataModule {

    val module = module {

        single<IForecastGateway> { ForecastGateway(get(), get(), get(), get(), get(), get()) }

        single<ICurrentDbRepository> { CurrentDbRepository(get()) }

        single<ICurrentLocationDbRepository> { CurrentLocationDbRepository(get()) }

        single<IDailiesDbRepository> { DailiesDbRepository(get()) }

        single<IHourliesDbRepository> { HourliesDbRepository(get()) }

        single<IUpdateTimeRepository> { UpdateTimeRepository(get()) }

        single<IForecastWebRepository> { ForecastWebRepository(get()) }

        single<IAddressRepository> { AddressRepository(get()) }
    }

}