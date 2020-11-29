package ru.nehodov.weatherforecast.repository

import android.location.Location
import android.os.Process
import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nehodov.weatherforecast.dao.*
import ru.nehodov.weatherforecast.database.ForecastDatabaseKot
import ru.nehodov.weatherforecast.entities.*
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApiKot
import ru.nehodov.weatherforecast.utils.CurrentToDailyConverterKot
import javax.inject.Inject

class ForecastRepositoryKot @Inject constructor(
        private val weatherApiKot: OpenWeatherApiKot,
        private val currentDao: CurrentDaoKot,
        private val dailyDao: DailyDaoKot,
        private val hourlyDao: HourlyDaoKot,
        private val currentLocationDao: CurrentLocationDaoKot,
        private val timeUpdateDao: TimeUpdateDaoKot
) {

    companion object {
        private const val TAG = "Repository"
    }

    fun updateForecast(location: Location) {
        val call: Call<Forecast> = weatherApiKot.getForecast(
                location.latitude.toString(),
                location.longitude.toString(),
                NetworkContract.WEATHER_API_KEY)
        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    ForecastDatabaseKot.DB_EXECUTOR_SERVICE.execute {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)

                        val dailyFromCurrent: Daily? =
                                CurrentToDailyConverterKot.convert(response.body()?.current)
                        currentDao.deleteAll()
                        currentDao.insert(dailyFromCurrent)

                        dailyDao.deleteAll()
                        dailyDao.insert(response.body()?.daily)

                        hourlyDao.deleteAll()
                        hourlyDao.insert(response.body()?.hourly)

                        val latitude: Double = response.body()?.latitude!!
                        val longitude: Double = response.body()?.longitude!!
                        Log.d(TAG, String.format("Repository updated current location,"
                                + " latitude: %f, longitude: %f", latitude, longitude))
                        currentLocationDao.deleteAll()
                        currentLocationDao.insert(CurrentLocation(
                                response.body()?.timezone,
                                latitude, longitude))

                        timeUpdateDao.deleteAll()
                        timeUpdateDao.insert(TimeUpdate(response.body()?.current?.dateTime))
                    }
                } else {
                    Log.d(TAG, response.code().toString())
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                if (t.message != null) {
                    Log.d(TAG, t.message!!)
                }
            }
        })
    }

    fun currentWeatherData(): LiveData<Daily?> {
        return currentDao.currentWeatherData()
    }

    fun dailyForecastData(): LiveData<MutableList<Daily?>?> {
        return dailyDao.dailyForecastData()
    }

    fun hourlyForecastData(): LiveData<MutableList<Hourly?>?> {
        return hourlyDao.hourlyData()
    }

    fun currentLocationData(): LiveData<CurrentLocation?> {
        return currentLocationDao.currentLocationData()
    }

    fun updateTimeData(): LiveData<String?> {
        return timeUpdateDao.timeUpdateData()
    }

    fun setCurrentLocation(currentLocation: CurrentLocation) {
        ForecastDatabaseKot.DB_EXECUTOR_SERVICE.execute {
            currentLocationDao.insert(currentLocation)
        }
    }
}