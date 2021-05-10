package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.CurrentLocation

interface ICurrentLocationDbRepository {
    fun insert(currentLocation: CurrentLocation)
    fun currentLocationData(): CurrentLocation
    fun deleteAll()
}

class CurrentLocationDbRepository(private val db: ForecastDatabase) :
    ICurrentLocationDbRepository {
    override fun insert(currentLocation: CurrentLocation) {
        db.currentLocationDao.insert(currentLocation)
    }

    override fun currentLocationData(): CurrentLocation {
        return db.currentLocationDao.currentLocationData()
    }

    override fun deleteAll() {
        db.currentLocationDao.deleteAll()
    }
}