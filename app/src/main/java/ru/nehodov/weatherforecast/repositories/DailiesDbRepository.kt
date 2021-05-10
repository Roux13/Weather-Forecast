package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Daily

interface IDailiesDbRepository {
    suspend fun deleteAll()
    suspend fun insert(dailies: List<Daily>)
    suspend fun getDailies(): List<Daily>
}

class DailiesDbRepository(private val db: ForecastDatabase) : IDailiesDbRepository {
    override suspend fun deleteAll() {
        db.dailyDao.deleteAll()
    }

    override suspend fun insert(dailies: List<Daily>) {
        db.dailyDao.insert(dailies)
    }

    override suspend fun getDailies(): List<Daily> {
        return db.dailyDao.dailyForecastData()
    }
}