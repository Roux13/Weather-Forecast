package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Current

interface ICurrentDbRepository {
    fun deleteAll()
    fun insert(current: Current)
    fun getCurrentWeather(): Current
}

class CurrentDbRepository(private val db: ForecastDatabase) : ICurrentDbRepository {
    override fun deleteAll() {
        db.currentDao.deleteAll()
    }

    override fun insert(current: Current) {
        db.currentDao.insert(current)
    }

    override fun getCurrentWeather(): Current {
        return db.currentDao.currentWeatherData()
    }
}