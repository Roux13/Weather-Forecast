package ru.nehodov.weatherforecast.repositories

import kotlinx.coroutines.flow.Flow
import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.CurrentLocation

interface ICurrentLocationDbRepository {
    suspend fun insert(currentLocation: CurrentLocation)
    fun currentLocationData(): Flow<CurrentLocation>
    suspend fun deleteAll()
}

class CurrentLocationDbRepository(private val db: ForecastDatabase) :
    ICurrentLocationDbRepository {
    override suspend fun insert(currentLocation: CurrentLocation) {
        db.currentLocationDao.insert(currentLocation)
    }

    override fun currentLocationData(): Flow<CurrentLocation> {
        return db.currentLocationDao.currentLocationData()
    }

    override suspend fun deleteAll() {
        db.currentLocationDao.deleteAll()
    }
}