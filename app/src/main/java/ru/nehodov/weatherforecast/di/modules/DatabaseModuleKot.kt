package ru.nehodov.weatherforecast.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.nehodov.weatherforecast.dao.*
import ru.nehodov.weatherforecast.database.ForecastDatabaseKot
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModuleKot {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ForecastDatabaseKot =
            ForecastDatabaseKot.getInstance(context)

    @Provides
    fun provideCurrentDao(database: ForecastDatabaseKot): CurrentDaoKot = database.currentDao

    @Provides
    fun provideDailyDao(database: ForecastDatabaseKot): DailyDaoKot = database.dailyDao

    @Provides
    fun provideHourlyDao(database: ForecastDatabaseKot): HourlyDaoKot = database.hourlyDao

    @Provides
    fun provideCurrentLocationDao(database: ForecastDatabaseKot): CurrentLocationDaoKot = database.currentLocationDao

    @Provides
    fun provideUpdateTimeDao(database: ForecastDatabaseKot): TimeUpdateDaoKot = database.updateTimeDao


}