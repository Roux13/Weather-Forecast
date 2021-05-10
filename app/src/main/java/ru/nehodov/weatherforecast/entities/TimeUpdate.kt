package ru.nehodov.weatherforecast.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeUpdate")
data class TimeUpdate(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: String = ""
)