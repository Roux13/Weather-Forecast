package ru.nehodov.weatherforecast;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import ru.nehodov.weatherforecast.repository.ForecastRepositoryKot;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ForecastUpdateWorker extends Worker {

    private static final String TAG = "ForecastUpdateWorker";

    ForecastRepositoryKot repository;

    private Location currentLocation;

    //    @WorkerInject
    public ForecastUpdateWorker(Context context,
                                WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "ForecastWorker work");
        requestCurrentLocation();
        if (currentLocation != null) {
            repository.updateForecast(currentLocation);
            Log.d("TAG", "ForecastWorker refresh location");
            return Worker.Result.success();
        }
        return Worker.Result.failure();
    }

    private void requestCurrentLocation() {
        currentLocation = null;
        LocationManager locationManager =
                (LocationManager) getApplicationContext()
                        .getSystemService(Context.LOCATION_SERVICE);
        boolean isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isFineLocationGranted = ContextCompat.checkSelfPermission(
                getApplicationContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED;
        boolean isBackgroundLocationGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            isBackgroundLocationGranted = ContextCompat.checkSelfPermission(
                    getApplicationContext(),
                    ACCESS_BACKGROUND_LOCATION) == PERMISSION_GRANTED;
        }
        Log.d(TAG, "isProviderEnabled " + isProviderEnabled);
        Log.d(TAG, "isFineLocationGranted " + isFineLocationGranted);
        Log.d(TAG, "isBackgroundLocationGranted " + isBackgroundLocationGranted);
        if (isProviderEnabled && isFineLocationGranted && isBackgroundLocationGranted) {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        Log.d(TAG, "Last location is " + currentLocation);
    }
}
