package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.CurrentLocation

interface ICurrentLocationDbRepository {
    suspend fun insert(currentLocation: CurrentLocation)
    suspend fun currentLocationData(): CurrentLocation
    suspend fun deleteAll()
}

class CurrentLocationDbRepository(private val db: ForecastDatabase) :
    ICurrentLocationDbRepository {
    override suspend fun insert(currentLocation: CurrentLocation) {
        db.currentLocationDao.insert(currentLocation)
    }

    override suspend fun currentLocationData(): CurrentLocation {
        return db.currentLocationDao.currentLocationData()
    }

    override suspend fun deleteAll() {
        db.currentLocationDao.deleteAll()
    }
}