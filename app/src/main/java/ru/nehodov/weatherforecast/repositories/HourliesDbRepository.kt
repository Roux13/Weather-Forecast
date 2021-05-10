package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Hourly

interface IHourliesDbRepository {
    suspend fun insert(hourlies: List<Hourly>)

    suspend fun hourlyData(): List<Hourly>

    suspend fun deleteAll()
}

class HourliesDbRepository(private val db: ForecastDatabase) : IHourliesDbRepository {
    override suspend fun insert(hourlies: List<Hourly>) {
        db.hourlyDao.insert(hourlies)
    }

    override suspend fun hourlyData(): List<Hourly> {
        return db.hourlyDao.hourlyData()
    }

    override suspend fun deleteAll() {
        db.hourlyDao.deleteAll()
    }
}