package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.TimeUpdate

interface IUpdateTimeRepository {
    suspend fun insert(timeUpdate: TimeUpdate)
    suspend fun timeUpdateData(): String
    suspend fun deleteAll()
}

class UpdateTimeRepository(private val db: ForecastDatabase) : IUpdateTimeRepository {
    override suspend fun insert(timeUpdate: TimeUpdate) {
        db.updateTimeDao.insert(timeUpdate)
    }

    override suspend fun timeUpdateData(): String {
        return db.updateTimeDao.timeUpdateData()
    }

    override suspend fun deleteAll() {
        db.updateTimeDao.deleteAll()
    }
}