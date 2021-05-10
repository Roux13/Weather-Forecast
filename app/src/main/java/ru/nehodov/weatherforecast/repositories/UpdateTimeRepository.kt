package ru.nehodov.weatherforecast.repositories

import ru.nehodov.weatherforecast.database.ForecastDatabase
import ru.nehodov.weatherforecast.entities.TimeUpdate

interface IUpdateTimeRepository {
    fun insert(timeUpdate: TimeUpdate)
    fun timeUpdateData(): String
    fun deleteAll()
}

class UpdateTimeRepository(private val db: ForecastDatabase) : IUpdateTimeRepository {
    override fun insert(timeUpdate: TimeUpdate) {
        db.updateTimeDao.insert(timeUpdate)
    }

    override fun timeUpdateData(): String {
        return db.updateTimeDao.timeUpdateData()
    }

    override fun deleteAll() {
        db.updateTimeDao.deleteAll()
    }
}