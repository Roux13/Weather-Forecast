package ru.nehodov.weatherforecast;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;

import ru.nehodov.weatherforecast.repository.ForecastRepository;

import static android.Manifest.permission.*;
import static android.content.pm.PackageManager.*;

public class ForecastUpdateWorker extends Worker {

    private static final String TAG = "ForecastUpdateWorker";

    private ActivityResultLauncher<String> resultLauncher;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final ForecastRepository repository;

    private Location currentLocation;

    public ForecastUpdateWorker(@NonNull Context context,
                                @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = new ForecastRepository(getApplicationContext());
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
//        CurrentLocation currentLocation = repository.getCurrentLocationWithoutLiveData();
//        if (currentLocation != null) {
//            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//            try {
//                String locationName = geocoder.getFromLocation(
//                        currentLocation.getLatitude(),
//                        currentLocation.getLongitude(), 1)
//                        .get(0)
//                        .getAddressLine(0);
//                this.currentLocation = new Location(locationName);
//                this.currentLocation.setLatitude(currentLocation.getLatitude());
//                this.currentLocation.setLongitude(currentLocation.getLongitude());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        Log.d(TAG, "Last location is " + currentLocation);
    }
}
