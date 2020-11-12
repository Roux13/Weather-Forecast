package ru.nehodov.weatherforecast;

import android.Manifest;
import android.content.Intent;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ru.nehodov.weatherforecast.databinding.MainActivityBinding;
import ru.nehodov.weatherforecast.settings.SettingsActivity;
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel;
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModelKot;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String REFRESH_REQUEST_TAG = "periodic_work_refresh_request";

    private ForecastViewModelKot viewModel;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ForecastViewModelKot.class);
        MainActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        WorkManager.getInstance(this).cancelAllWork();
        requestPermissions();
        init();
    }

    private void requestPermissions() {
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (!isGranted) {
                        if (isLocationDisabled()) {
                            showAlertMessageNoGps();
                        }
                        Snackbar.make(
                                findViewById(R.id.mainActivity),
                                R.string.permission_rationale,
                                Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PERMISSION_DENIED) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            }
            requestPermissions();
        }
    }

    private void init() {
        viewModel.getCurrentLocation().observe(this, currentLocation -> {
            if (currentLocation != null) {
                Log.d(TAG, String.format(
                        "Current location is not null, latitude: %f, longitude: %f",
                        currentLocation.getLatitude(), currentLocation.getLongitude()));
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    double latitude = currentLocation.getLatitude()
                            == 0.0 ? 1.0 : currentLocation.getLatitude();
                    double longitude = currentLocation.getLongitude()
                            == 0.0 ? 1.0 : currentLocation.getLongitude();
                    if (!geocoder.getFromLocation(latitude, longitude, 1).isEmpty()) {
                        String locationName = geocoder.getFromLocation(latitude, longitude, 1)
                                .get(0)
                                .getAddressLine(0);
                        Log.d(TAG, locationName);
                        viewModel.setLocationTitle(locationName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d(TAG, "CurrentLocation is null");
            }
        });
    }

    private void requestCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            requestCurrentLocation();
        } else {
            if (locationCallback == null) {
                locationCallback = configureLocationCallback(viewModel);
            }
            fusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(this);
            if (isUpdateEnabled()) {
                fusedLocationProviderClient.requestLocationUpdates(
                        makeRequest(),
                        locationCallback,
                        Looper.getMainLooper());
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        viewModel.updateForecast(location);
                    } else {
                        showAlertMessageNoGps();
                    }
                });
            }
        }
    }

    public LocationRequest makeRequest() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(TimeUnit.MINUTES.toMillis(getUpdateInterval()));
        new LocationSettingsRequest.Builder().addLocationRequest(request);
        return request;
    }

    public LocationCallback configureLocationCallback(ForecastViewModelKot viewModel) {
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    showAlertMessageNoGps();
                    return;
                }
                if (viewModel != null) {
                    viewModel.updateForecast(locationResult.getLocations().get(0));
                }
            }

        };
    }

    private void runUpdateWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest
                .Builder(ForecastUpdateWorker.class, getUpdateInterval(), TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .addTag(REFRESH_REQUEST_TAG)
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                REFRESH_REQUEST_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        WorkManager.getInstance(this).cancelAllWork();
        final LocationManager locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (isLocationDisabled()) {
                showAlertMessageNoGps();
            }
        } else {
            requestCurrentLocation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isUpdateEnabled()) {
            runUpdateWorker();
        } else {
            WorkManager.getInstance(this).cancelAllWork();
        }
    }

    private boolean isUpdateEnabled() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(getString(R.string.switch_enable_update_preferences_key), true);
    }

    private int getUpdateInterval() {
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.edit_text_period_preferences_key), "5"));
    }

    private void showAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS is disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        (dialog, id) -> startActivity(new Intent(ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean isLocationDisabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return !((LocationManager) getSystemService(LOCATION_SERVICE)).isLocationEnabled();
        } else {
            return true;
        }
    }

    public void onSettingsClick(MenuItem item) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

}