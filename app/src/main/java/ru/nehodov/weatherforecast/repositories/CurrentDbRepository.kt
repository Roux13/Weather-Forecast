package ru.nehodov.weatherforecast.repositories

import kotlinx.coroutines.flow.Flow
import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Current

interface ICurrentDbRepository {
    suspend fun deleteAll()
    suspend fun insert(current: Current)
    fun getCurrentWeather(): Flow<Current>
}

class CurrentDbRepository(private val db: ForecastDatabase) : ICurrentDbRepository {
    override suspend fun deleteAll() {
        db.currentDao.deleteAll()
    }

    override suspend fun insert(current: Current) {
        db.currentDao.insert(current)
    }

    override fun getCurrentWeather(): Flow<Current> {
        return db.currentDao.currentWeatherData()
    }
}