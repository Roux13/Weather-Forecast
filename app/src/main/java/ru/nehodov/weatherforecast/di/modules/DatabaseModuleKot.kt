package ru.nehodov.weatherforecast.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.nehodov.weatherforecast.dao.*
import ru.nehodov.weatherforecast.database.ForecastDatabase
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModuleKot {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ForecastDatabase =
            ForecastDatabase.getInstance(context)

    @Provides
    fun provideCurrentDao(database: ForecastDatabase): CurrentDao = database.currentDao

    @Provides
    fun provideDailyDao(database: ForecastDatabase): DailyDao = database.dailyDao

    @Provides
    fun provideHourlyDao(database: ForecastDatabase): HourlyDao = database.hourlyDao

    @Provides
    fun provideCurrentLocationDao(database: ForecastDatabase): CurrentLocationDao = database.currentLocationDao

    @Provides
    fun provideUpdateTimeDao(database: ForecastDatabase): UpdateTimeDao = database.updateTimeDao


}