package ru.nehodov.weatherforecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nehodov.weatherforecast.dao.*
import ru.nehodov.weatherforecast.entities.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [
    Current::class,
    Daily::class,
    Hourly::class,
    CurrentLocation::class,
    TimeUpdate::class],
        version = 4, exportSchema = false)
abstract class ForecastDatabaseKot : RoomDatabase() {


    companion object {
        private const val DB_NAME = "forecast_db"

        private const val NUMBER_OF_THREADS = 4

        @JvmField
        val DB_EXECUTOR_SERVICE: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        private lateinit var INSTANCE: ForecastDatabaseKot

        @JvmStatic
        fun getInstance(applicationContext: Context): ForecastDatabaseKot {
            synchronized(ForecastDatabaseKot::class) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                            applicationContext,
                            ForecastDatabaseKot::class.java,
                            DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }

    abstract val currentDao: CurrentDaoKot

    abstract val dailyDao: DailyDaoKot

    abstract val hourlyDao: HourlyDaoKot

    abstract val currentLocationDao: CurrentLocationDaoKot

    abstract val updateTimeDao: TimeUpdateDaoKot

}