package ru.nehodov.weatherforecast;


import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import ru.nehodov.weatherforecast.database.ForecastDatabase;
import ru.nehodov.weatherforecast.repository.ForecastRepository;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ForecastUpdateWorker extends Worker {

    private ActivityResultLauncher<String> resultLauncher;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private ForecastRepository repository;

    private Location currentLocation;

    public ForecastUpdateWorker(@NonNull Context context,
                                @NonNull WorkerParameters workerParams,
                                Application application) {
        super(context, workerParams);
        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(getApplicationContext());
        requestCurrentLocation();
        ForecastDatabase db = ForecastDatabase.getInstance(application);
        repository = new ForecastRepository(
                db.getCurrentDao(), db.getDailyDao(), db.getHourlyDao());
    }

    @NonNull
    @Override
    public Result doWork() {
        if (currentLocation != null) {
            repository.refreshForecast(currentLocation);
        }
        return null;
    }

    private void requestCurrentLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PERMISSION_GRANTED) {
            currentLocation = fusedLocationProviderClient.getLastLocation().getResult();
        }
    }
}
