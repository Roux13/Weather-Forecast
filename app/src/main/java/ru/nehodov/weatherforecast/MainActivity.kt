package ru.nehodov.weatherforecast

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import androidx.work.*
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import ru.nehodov.weatherforecast.databinding.MainActivityBinding
import ru.nehodov.weatherforecast.settings.SettingsActivity
import ru.nehodov.weatherforecast.viewmodels.ForecastViewModel
import java.util.concurrent.TimeUnit

@KoinApiExtension
class MainActivity : AppCompatActivity(), KoinComponent {

    companion object {
        private const val TAG = "MainActivity"
        private const val REFRESH_REQUEST_TAG = "periodic_work_refresh_request"
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

    private val forecastViewModel: ForecastViewModel by viewModel()

    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private var locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        binding.viewmodel = forecastViewModel
        WorkManager.getInstance(this).cancelAllWork()
        requestPermissions()
    }

    private fun requestPermissions() {
        requestPermissionLauncher = registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean? ->
            if (!isGranted!!) {
                if (isLocationDisabled()) {
                    showAlertMessageNoGps()
                }
                Snackbar.make(
                    findViewById(R.id.mainActivity),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher!!.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissionLauncher!!.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            requestPermissions()
        }
    }

    private fun requestCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher!!.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            requestCurrentLocation()
        } else {
            if (locationCallback == null) {
                locationCallback = configureLocationCallback(forecastViewModel)
            }
            fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this)
            if (isUpdateEnabled()) {
                fusedLocationProviderClient!!.requestLocationUpdates(
                    makeRequest(),
                    locationCallback,
                    Looper.getMainLooper()
                )
            } else {
                fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
                fusedLocationProviderClient!!.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            forecastViewModel.updateForecast(location)
                        } else {
                            showAlertMessageNoGps()
                        }
                    }
            }
        }
    }

    private fun makeRequest(): LocationRequest {
        val request = LocationRequest.create()
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        request.interval = TimeUnit.MINUTES.toMillis(getUpdateInterval())
        LocationSettingsRequest.Builder().addLocationRequest(request)
        return request
    }

    private fun configureLocationCallback(viewModel: ForecastViewModel): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                viewModel.updateForecast(locationResult.locations[0])
            }
        }
    }

    private fun runUpdateWorker() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(
                ForecastUpdateWorker::class.java,
                getUpdateInterval(),
                TimeUnit.MINUTES
            ).setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .addTag(REFRESH_REQUEST_TAG)
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            REFRESH_REQUEST_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    override fun onResume() {
        super.onResume()
        WorkManager.getInstance(this).cancelAllWork()
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (isLocationDisabled()) {
                showAlertMessageNoGps()
            }
        } else {
            requestCurrentLocation()
        }
    }

    override fun onStop() {
        super.onStop()
        if (locationCallback != null) {
            fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isUpdateEnabled()) {
            runUpdateWorker()
        } else {
            WorkManager.getInstance(this).cancelAllWork()
        }
    }

    private fun isUpdateEnabled(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(getString(R.string.switch_enable_update_preferences_key), true)
    }

    private fun getUpdateInterval(): Long {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getString(getString(R.string.edit_text_period_preferences_key), "5")?.toLong() ?: 5L
    }

    private fun showAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS is disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog: DialogInterface?, id: Int ->
                startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }
            .setNegativeButton("No") { dialog: DialogInterface, id: Int -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    private fun isLocationDisabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            !(getSystemService(LOCATION_SERVICE) as LocationManager).isLocationEnabled
        } else {
            true
        }
    }

    fun onSettingsClick(item: MenuItem?) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}