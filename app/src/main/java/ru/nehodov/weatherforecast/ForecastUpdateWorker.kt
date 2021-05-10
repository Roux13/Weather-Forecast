package ru.nehodov.weatherforecast

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.nehodov.weatherforecast.repositories.IForecastGateway

class ForecastUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), KoinComponent {

    companion object {
        private const val TAG = "ForecastUpdateWorker"
    }

    private val forecastGateway: IForecastGateway by inject()

    var currentLocation: Location? = null

    override fun doWork(): Result {
        Log.d(TAG, "ForecastWorker work")
        requestCurrentLocation()
        if (currentLocation != null) {
            forecastGateway.updateForecast(currentLocation!!)
            Log.d(TAG, "ForecastWorker refresh location")
            return Result.success()
        }
        return Result.failure()
    }

    private fun requestCurrentLocation() {
        currentLocation = null
        val locationManager = applicationContext
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isFineLocationGranted = ContextCompat.checkSelfPermission(
            applicationContext, permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        var isBackgroundLocationGranted = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isBackgroundLocationGranted = ContextCompat.checkSelfPermission(
                applicationContext,
                permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
        Log.d(TAG, "isProviderEnabled $isProviderEnabled")
        Log.d(TAG, "isFineLocationGranted $isFineLocationGranted")
        Log.d(TAG, "isBackgroundLocationGranted $isBackgroundLocationGranted")
        if (isProviderEnabled && isFineLocationGranted && isBackgroundLocationGranted) {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        Log.d(TAG, "Last location is $currentLocation")
    }
}