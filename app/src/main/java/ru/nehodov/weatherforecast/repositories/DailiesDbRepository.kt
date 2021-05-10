package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Daily

interface IDailiesDbRepository {
    fun deleteAll()
    fun insert(dailies: List<Daily>)
    fun getDailies(): List<Daily>
}

class DailiesDbRepository(private val db: ForecastDatabase) : IDailiesDbRepository {
    override fun deleteAll() {
        db.dailyDao.deleteAll()
    }

    override fun insert(dailies: List<Daily>) {
        db.dailyDao.insert(dailies)
    }

    override fun getDailies(): List<Daily> {
        return db.dailyDao.dailyForecastData()
    }
}