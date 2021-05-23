package ru.nehodov.weatherforecast.repositories

import kotlinx.coroutines.flow.Flow
import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Daily

interface IDailiesDbRepository {
    suspend fun deleteAll()
    suspend fun insert(dailies: List<Daily>)
    fun getDailies(): Flow<List<Daily>>
}

class DailiesDbRepository(private val db: ForecastDatabase) : IDailiesDbRepository {
    override suspend fun deleteAll() {
        db.dailyDao.deleteAll()
    }

    override suspend fun insert(dailies: List<Daily>) {
        db.dailyDao.insert(dailies)
    }

    override fun getDailies(): Flow<List<Daily>> {
        return db.dailyDao.dailyForecastData()
    }
}