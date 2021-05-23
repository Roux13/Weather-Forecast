package ru.nehodov.weatherforecast.repositories

import android.location.Geocoder
import ru.nehodov.weatherforecast.entities.CurrentLocation
import java.io.IOException

interface IAddressRepository {
    fun getCurrentAddress(currentLocation: CurrentLocation): String
}

class AddressRepository(private val geocoder: Geocoder) : IAddressRepository {
    override fun getCurrentAddress(currentLocation: CurrentLocation): String {
        return try {
            val latitude =
                if (currentLocation.latitude == 0.0) 1.0 else currentLocation.latitude
            val longitude =
                if (currentLocation.longitude == 0.0) 1.0 else currentLocation.longitude
            if (geocoder.getFromLocation(latitude, longitude, 1).isNotEmpty()) {
                val locationName =
                    geocoder.getFromLocation(latitude, longitude, 1)[0]
                        .getAddressLine(0)
                locationName
            } else {
                ""
            }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}