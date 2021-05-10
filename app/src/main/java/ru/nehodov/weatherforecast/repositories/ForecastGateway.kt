package ru.nehodov.weatherforecast.repositories

import android.location.Location
import android.os.Process
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.CurrentLocation
import ru.nehodov.weatherforecast.entities.Forecast
import ru.nehodov.weatherforecast.entities.TimeUpdate

interface IForecastGateway {
    fun updateForecast(location: Location)
}

class ForecastGateway(
    private val forecastWebRepository: IForecastWebRepository,
    private val currentDbRepository: ICurrentDbRepository,
    private val dailiesDbRepository: IDailiesDbRepository,
    private val hourliesDbRepository: IHourliesDbRepository,
    private val currentLocationDbRepository: ICurrentLocationDbRepository,
    private val updateTimeRepository: IUpdateTimeRepository,
) : IForecastGateway {

    companion object {
        private const val TAG = "Repository"
    }

    override fun updateForecast(location: Location) {
        val call = forecastWebRepository.updateForecast(location)
        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    ForecastDatabase.DB_EXECUTOR_SERVICE.execute {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)

                        response.body()?.let {
                            currentDbRepository.deleteAll()
                            currentDbRepository.insert(it.current)

                            dailiesDbRepository.deleteAll()
                            dailiesDbRepository.insert(it.daily)

                            hourliesDbRepository.deleteAll()
                            hourliesDbRepository.insert(it.hourly)

                            val latitude: Double = it.latitude
                            val longitude: Double = it.longitude
                            Log.d(
                                TAG, String.format(
                                    "Repository updated current location,"
                                            + " latitude: %f, longitude: %f", latitude, longitude
                                )
                            )
                            currentLocationDbRepository.deleteAll()
                            currentLocationDbRepository.insert(
                                CurrentLocation(
                                    timezone = response.body()?.timezone ?: "",
                                    latitude = latitude,
                                    longitude = longitude
                                )
                            )

                            updateTimeRepository.deleteAll()
                            updateTimeRepository.insert(TimeUpdate(time = it.current.dateTime))
                        }
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
}