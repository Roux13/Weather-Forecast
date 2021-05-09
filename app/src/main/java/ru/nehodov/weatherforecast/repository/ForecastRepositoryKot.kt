package ru.nehodov.weatherforecast.repository

import android.location.Location
import android.os.Process
import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nehodov.weatherforecast.database.ForecastDatabaseKot
import ru.nehodov.weatherforecast.entities.*
import ru.nehodov.weatherforecast.network.NetworkContract
import ru.nehodov.weatherforecast.network.OpenWeatherApiKot
import ru.nehodov.weatherforecast.utils.CurrentToDailyConverterKot

class ForecastRepositoryKot(
    private val db: ForecastDatabaseKot,
    private val weatherApiKot: OpenWeatherApiKot
) {

    companion object {
        private const val TAG = "Repository"
    }

    fun updateForecast(location: Location) {
        val call: Call<Forecast> = weatherApiKot.getForecast(
            location.latitude.toString(),
            location.longitude.toString(),
            NetworkContract.WEATHER_API_KEY
        )
        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    ForecastDatabaseKot.DB_EXECUTOR_SERVICE.execute {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)

                        val dailyFromCurrent: Daily? =
                            CurrentToDailyConverterKot.convert(response.body()?.current)
                        db.currentDao.deleteAll()
                        db.currentDao.insert(dailyFromCurrent)

                        db.dailyDao.deleteAll()
                        db.dailyDao.insert(response.body()?.daily)

                        db.hourlyDao.deleteAll()
                        db.hourlyDao.insert(response.body()?.hourly)

                        val latitude: Double = response.body()?.latitude!!
                        val longitude: Double = response.body()?.longitude!!
                        Log.d(
                            TAG, String.format(
                                "Repository updated current location,"
                                        + " latitude: %f, longitude: %f", latitude, longitude
                            )
                        )
                        db.currentLocationDao.deleteAll()
                        db.currentLocationDao.insert(
                            CurrentLocation(
                                response.body()?.timezone,
                                latitude, longitude
                            )
                        )

                        db.updateTimeDao.deleteAll()
                        db.updateTimeDao.insert(TimeUpdate(response.body()?.current?.dateTime))
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
        return db.currentDao.currentWeatherData()
    }

    fun dailyForecastData(): LiveData<MutableList<Daily?>?> {
        return db.dailyDao.dailyForecastData()
    }

    fun hourlyForecastData(): LiveData<MutableList<Hourly?>?> {
        return db.hourlyDao.hourlyData()
    }

    fun currentLocationData(): LiveData<CurrentLocation?> {
        return db.currentLocationDao.currentLocationData()
    }

    fun updateTimeData(): LiveData<String?> {
        return db.updateTimeDao.timeUpdateData()
    }

    fun setCurrentLocation(currentLocation: CurrentLocation) {
        ForecastDatabaseKot.DB_EXECUTOR_SERVICE.execute {
            db.currentLocationDao.insert(currentLocation)
        }
    }
}