package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.Hourly

interface IHourliesDbRepository {
    fun insert(hourlies: List<Hourly>)

    fun hourlyData(): List<Hourly>

    fun deleteAll()
}

class HourliesDbRepository(private val db: ForecastDatabase) : IHourliesDbRepository {
    override fun insert(hourlies: List<Hourly>) {
        db.hourlyDao.insert(hourlies)
    }

    override fun hourlyData(): List<Hourly> {
        return db.hourlyDao.hourlyData()
    }

    override fun deleteAll() {
        db.hourlyDao.deleteAll()
    }
}