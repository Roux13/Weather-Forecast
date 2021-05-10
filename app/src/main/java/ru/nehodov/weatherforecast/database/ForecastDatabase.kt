package ru.nehodov.weatherforecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nehodov.weatherforecast.dao.*
import ru.nehodov.weatherforecast.entities.*
import ru.nehodov.weatherforecast.utils.WeatherArrayConverter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
    entities = [
        Current::class,
        Daily::class,
        Hourly::class,
        CurrentLocation::class,
        TimeUpdate::class],
    version = 9, exportSchema = false
)
@TypeConverters(WeatherArrayConverter::class)
abstract class ForecastDatabase : RoomDatabase() {


    companion object {
        private const val DB_NAME = "forecast_db"

        private const val NUMBER_OF_THREADS = 4

        @JvmField
        val DB_EXECUTOR_SERVICE: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        private lateinit var INSTANCE: ForecastDatabase

        @JvmStatic
        fun getInstance(applicationContext: Context): ForecastDatabase {
            synchronized(ForecastDatabase::class) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        applicationContext,
                        ForecastDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    abstract val currentDao: CurrentDao

    abstract val dailyDao: DailyDao

    abstract val hourlyDao: HourlyDao

    abstract val currentLocationDao: CurrentLocationDao

    abstract val updateTimeDao: TimeUpdateDao

}